package com.bitmakersbd.biyebari.server.repository;

import com.bitmakersbd.biyebari.server.model.Area;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    public List<Area> findAllByIsActive(State isActive);

    public Page<Area> findAllByIsActiveAndDistrictId(State isActive, Long districtId, Pageable pageable);
}
