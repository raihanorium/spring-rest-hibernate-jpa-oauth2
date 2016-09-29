package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Area;
import com.bitmakersbd.biyebari.server.repository.AreaRepository;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AreaServiceImpl implements AreaService {
    @Autowired
    AreaRepository areaRepository;

    @Autowired
    Messages messages;

    @Override
    public Page getAll() throws Exception {
        List<Area> areas = areaRepository.findAllByIsActive(State.active);
        return new PageImpl<>(areas);
    }

    @Override
    public Area get(Long id) throws Exception {
        Area area = areaRepository.findOne(id);

        if (area.getIsActive() != State.active) {
            throw new Exception(messages.getMessage("area.not.active"));
        }

        return area;
    }

    @Transactional(readOnly = true)
    @Override
    public Page getAllByDistrictId(Long districtId, Pageable pageable) throws Exception {
        return areaRepository.findAllByIsActiveAndDistrictId(State.active, districtId, pageable);
    }
}
