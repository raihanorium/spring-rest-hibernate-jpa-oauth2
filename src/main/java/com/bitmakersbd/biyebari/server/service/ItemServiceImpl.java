package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Item;
import com.bitmakersbd.biyebari.server.model.ItemImage;
import com.bitmakersbd.biyebari.server.model.Vendor;
import com.bitmakersbd.biyebari.server.repository.ItemRepository;
import com.bitmakersbd.biyebari.server.repository.VendorRepository;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.State;
import com.bitmakersbd.biyebari.server.util.UploadType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.*;

public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    VendorRepository vendorRepository;

    @Autowired
    Messages messages;

    @Autowired
    FileUploadService fileUploadService;

    @Transactional(readOnly = true)
    @Override
    public Page getAll(Collection<State> states, Pageable pageable) throws Exception {
        return itemRepository.findAllByIsActiveIn(states, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Item get(Long id) throws Exception {
        return itemRepository.findOne(id);
    }

    @Transactional
    @Override
    public Item create(Item item) throws Exception {
        // item must be created by a vendor. because items are referenced with vendor
        Vendor vendorInDb = vendorRepository.findOne(item.getCreatedBy().getId());
        if (vendorInDb == null) throw new Exception(messages.getMessage("vendor.not.found"));

        return itemRepository.save(item);
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        Item itemInDb = itemRepository.findOne(id);
        if (itemInDb == null) throw new Exception(messages.getMessage("item.not.found"));

        deleteImageFiles(itemInDb);

        itemRepository.delete(id);
    }

    @Transactional
    @Override
    public Item update(Item item) throws Exception {
        Item itemInDb = itemRepository.findOne(item.getId());
        if (itemInDb == null) throw new Exception(messages.getMessage("item.not.found"));

        // update only fields which are allowed to update
        itemInDb.setName(item.getName());
        itemInDb.setDescription(item.getDescription());
        itemInDb.setCategory(item.getCategory());
        itemInDb.setPrice(item.getPrice());
        itemInDb.setUnit(item.getUnit());
        itemInDb.setStock(item.getStock());
        itemInDb.setIsActive(item.getIsActive());
        itemInDb.setUpdatedBy(item.getUpdatedBy());

        return itemRepository.save(itemInDb);
    }

    @Transactional
    @Override
    public Item addImages(Long id, List<MultipartFile> images) throws Exception {
        Item itemInDb = itemRepository.findOne(id);
        if (itemInDb == null) throw new Exception(messages.getMessage("item.not.found"));

        List<String> uploadedFiles = fileUploadService.uploadFile(UploadType.ITEM_IMAGE, images, true, id.toString());

        Set<ItemImage> itemImages = new HashSet<>();
        for (String file : uploadedFiles) {
            ItemImage itemImage = new ItemImage();
            itemImage.setItem(itemInDb);
            itemImage.setImageFileName(file);
            itemImage.setCreatedBy(itemInDb.getCreatedBy().getId());
            itemImages.add(itemImage);
        }

        itemInDb.getImages().addAll(itemImages);

        itemRepository.save(itemInDb);

        return itemInDb;
    }

    @Transactional
    @Override
    public Item deleteImages(Long id, List<Long> imageIds) throws Exception {
        if (imageIds.size() < 1) throw new Exception(messages.getMessage("nothing.to.delete"));

        Item itemInDb = itemRepository.findOne(id);
        if (itemInDb == null) throw new Exception(messages.getMessage("item.not.found"));

        if ((itemInDb.getImages() == null) || (itemInDb.getImages().size() < 1))
            throw new Exception(messages.getMessage("item.no.image"));

        Set<ItemImage> imagesToDelete = new HashSet<>();
        for (ItemImage image : itemInDb.getImages()) {
            if (imageIds.contains(image.getId())) {
                imagesToDelete.add(image);
            }
        }

        if (imagesToDelete.size() < 1)
            throw new Exception(MessageFormat.format(messages.getMessage("item.image.does.not.exist"), imageIds));

        itemInDb.getImages().removeAll(imagesToDelete);

        // delete image files
        Item tempItem = new Item();
        tempItem.setId(itemInDb.getId());
        tempItem.setImages(imagesToDelete);
        deleteImageFiles(tempItem);

        itemRepository.save(itemInDb);

        return itemInDb;
    }

    private void deleteImageFiles(Item itemInDb) throws Exception {
        if (itemInDb.getImages() != null) {
            List<String> fileNames = new ArrayList<>();
            for (ItemImage image : itemInDb.getImages()) {
                fileNames.add(image.getImageFileName());
            }
            fileUploadService.deleteFile(UploadType.ITEM_IMAGE, fileNames, true, itemInDb.getId().toString());
        }
    }
}
