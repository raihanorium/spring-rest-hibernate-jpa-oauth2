package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Unit;
import com.bitmakersbd.biyebari.server.repository.UnitRepository;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class UnitServiceImpl implements UnitService {
    @Autowired
    UnitRepository unitRepository;

    @Autowired
    Messages messages;

    @Transactional(readOnly = true)
    @Override
    public Page getAll(Pageable pageable) throws Exception {
        return unitRepository.findAllByIsActive(State.active, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Unit get(Long id) throws Exception {
        Unit unit = unitRepository.findOne(id);

        if (unit.getIsActive() != State.active) {
            throw new Exception(messages.getMessage("unit.not.active"));
        }

        return unit;
    }

    @Transactional
    @Override
    public Unit create(Unit unit) throws Exception {
        return unitRepository.save(unit);
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        Unit unitInDb = unitRepository.findOne(id);
        if (unitInDb == null) throw new Exception(messages.getMessage("unit.not.found"));

        unitRepository.delete(id);
    }

    @Transactional
    @Override
    public Unit update(Unit unit) throws Exception {
        Unit unitInDb = unitRepository.findOne(unit.getId());
        if (unitInDb == null) throw new Exception(messages.getMessage("unit.not.found"));

        // update only fields which are allowed to update
        unitInDb.setName(unit.getName());
        unitInDb.setIsActive(unit.getIsActive());
        unitInDb.setUpdatedBy(unit.getUpdatedBy());

        return unitRepository.save(unitInDb);
    }
}
