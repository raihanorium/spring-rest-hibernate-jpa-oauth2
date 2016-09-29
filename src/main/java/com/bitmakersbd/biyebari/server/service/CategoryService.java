package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page getAll() throws Exception;

    Page getAllLeaves(Pageable pageable) throws Exception;

    Category get(Long id) throws Exception;

    Category create(Category category) throws Exception;

    void delete(Long id) throws Exception;

    Category update(Category category) throws Exception;

    Page getAllChildren(Long categoryId, Pageable pageable) throws Exception;

    Page getAllItems(Long categoryId, Pageable pageable) throws Exception;
}
