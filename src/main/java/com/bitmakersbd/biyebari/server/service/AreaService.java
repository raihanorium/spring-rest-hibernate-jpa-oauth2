package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AreaService {
    Page getAll() throws Exception;

    Area get(Long id) throws Exception;

    Page getAllByDistrictId(Long districtId, Pageable pageable) throws Exception;
}
