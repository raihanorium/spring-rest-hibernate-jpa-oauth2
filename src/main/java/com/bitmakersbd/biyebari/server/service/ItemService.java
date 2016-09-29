package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Item;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    Page getAll(Collection<State> states, Pageable pageable) throws Exception;

    Item get(Long id) throws Exception;

    Item create(Item item) throws Exception;

    void delete(Long id) throws Exception;

    Item update(Item item) throws Exception;

    Item addImages(Long id, List<MultipartFile> images) throws Exception;

    Item deleteImages(Long id, List<Long> imageIds) throws Exception;
}
