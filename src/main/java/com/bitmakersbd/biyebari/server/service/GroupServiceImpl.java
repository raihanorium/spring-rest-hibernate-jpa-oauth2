package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.Group;
import com.bitmakersbd.biyebari.server.repository.GroupRepository;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    Messages messages;

    @Transactional(readOnly = true)
    @Override
    public Page getAll(Pageable pageable) throws Exception {
        return groupRepository.findAllByIsActive(State.active, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Group get(Long id) throws Exception {
        Group group = groupRepository.findOne(id);

        if (group.getIsActive() != State.active) {
            throw new Exception(messages.getMessage("group.not.active"));
        }

        return group;
    }

    @Transactional
    @Override
    public Group create(Group group) throws Exception {
        return groupRepository.save(group);
    }

    @Transactional
    @Override
    public void delete(Long id) throws Exception {
        Group groupInDb = groupRepository.findOne(id);
        if (groupInDb == null) throw new Exception(messages.getMessage("group.not.found"));

        groupRepository.delete(id);
    }

    @Transactional
    @Override
    public Group update(Group group) throws Exception {
        Group groupInDb = groupRepository.findOne(group.getId());
        if (groupInDb == null) throw new Exception(messages.getMessage("group.not.found"));

        // update only fields which are allowed to update
        groupInDb.setName(group.getName());
        groupInDb.setDescription(group.getDescription());
        groupInDb.setIsActive(group.getIsActive());
        groupInDb.setParent(group.getParent());
        groupInDb.setcOrder(group.getcOrder());
        groupInDb.setDefaultImage(group.getDefaultImage());
        groupInDb.setInfoFormat(group.getInfoFormat());
        groupInDb.setIsBudget(group.getIsBudget());
        groupInDb.setUpdatedBy(group.getUpdatedBy());

        return groupRepository.save(groupInDb);
    }
}
