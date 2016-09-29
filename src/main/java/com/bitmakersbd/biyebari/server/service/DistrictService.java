package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DistrictService {
    Page getAll(Pageable pageable) throws Exception;

    District get(Long id) throws Exception;
}
