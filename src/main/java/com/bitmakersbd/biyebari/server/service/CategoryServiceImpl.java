package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Category;
import com.bitmakersbd.biyebari.server.repository.CategoryRepository;
import com.bitmakersbd.biyebari.server.repository.ItemRepository;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    Messages messages;

    @Transactional(readOnly = true)
    @Override
    public Page getAll() throws Exception {
        List<Category> categories = categoryRepository.findAllByIsActive(State.active);
        return new PageImpl<>(categories);
    }

    @Transactional(readOnly = true)
    @Override
    public Page getAllLeaves(Pageable pageable) throws Exception {
        return categoryRepository.findAllLeaves(State.active, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Category get(Long id) throws Exception {
        Category category = categoryRepository.findOne(id);

        if (category.getIsActive() != State.active) {
            throw new Exception(messages.getMessage("category.not.active"));
        }

        return category;
    }

    @Transactional
    @Override
    public Category create(Category category) throws Exception {
        return categoryRepository.save(category);
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        Category categoryInDb = categoryRepository.findOne(id);
        if (categoryInDb == null) throw new Exception(messages.getMessage("category.not.found"));

        categoryRepository.delete(id);
    }

    @Transactional
    @Override
    public Category update(Category category) throws Exception {
        Category categoryInDb = categoryRepository.findOne(category.getId());
        if (categoryInDb == null) throw new Exception(messages.getMessage("category.not.found"));

        // update only fields which are allowed to update
        categoryInDb.setName(category.getName());
        categoryInDb.setDescription(category.getDescription());
        categoryInDb.setIsActive(category.getIsActive());
        categoryInDb.setGroupId(category.getGroupId());
        categoryInDb.setParent(category.getParent());
        categoryInDb.setcOrder(category.getcOrder());
        categoryInDb.setDefaultImage(category.getDefaultImage());
        categoryInDb.setUpdatedBy(category.getUpdatedBy());

        return categoryRepository.save(categoryInDb);
    }

    @Transactional(readOnly = true)
    @Override
    public Page getAllChildren(Long categoryId, Pageable pageable) throws Exception {
        Category categoryInDb = categoryRepository.findOne(categoryId);
        if (categoryInDb == null) throw new Exception(messages.getMessage("category.not.found"));

        if (categoryInDb.getIsActive() != State.active) {
            throw new Exception(messages.getMessage("category.not.active"));
        }

        return categoryRepository.findAllByParentAndIsActive(categoryId, State.active, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page getAllItems(Long categoryId, Pageable pageable) throws Exception {
        Category categoryInDb = categoryRepository.findOne(categoryId);
        if (categoryInDb == null) throw new Exception(messages.getMessage("category.not.found"));

        if (categoryInDb.getIsActive() != State.active) {
            throw new Exception(messages.getMessage("category.not.active"));
        }

        return itemRepository.findAllByCategoryIdAndIsActive(categoryId, State.active, pageable);
    }
}
