package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnitService {
    Page getAll(Pageable pageable) throws Exception;

    Unit get(Long id) throws Exception;

    Unit create(Unit unit) throws Exception;

    void delete(Long id) throws Exception;

    Unit update(Unit unit) throws Exception;
}
