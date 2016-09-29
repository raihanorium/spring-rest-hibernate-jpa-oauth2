package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GroupService {
    Page getAll(Pageable pageable) throws Exception;

    Group get(Long id) throws Exception;

    Group create(Group group) throws Exception;

    void delete(Long id) throws Exception;

    Group update(Group group) throws Exception;
}
