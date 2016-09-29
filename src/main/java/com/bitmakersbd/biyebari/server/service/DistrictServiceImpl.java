package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.District;
import com.bitmakersbd.biyebari.server.repository.DistrictRepository;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class DistrictServiceImpl implements DistrictService {
    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    Messages messages;

    @Transactional(readOnly = true)
    @Override
    public Page getAll(Pageable pageable) throws Exception {
        return districtRepository.findAllByIsActive(State.active, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public District get(Long id) throws Exception {
        District district = districtRepository.findOne(id);

        if (district.getIsActive() != State.active) {
            throw new Exception(messages.getMessage("district.not.active"));
        }

        return district;
    }
}
