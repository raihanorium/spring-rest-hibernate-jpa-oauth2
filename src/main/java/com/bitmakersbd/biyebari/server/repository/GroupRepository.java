package com.bitmakersbd.biyebari.server.repository;

import com.bitmakersbd.biyebari.server.model.Group;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    public Page<Group> findAllByIsActive(State isActive, Pageable pageable);
}
