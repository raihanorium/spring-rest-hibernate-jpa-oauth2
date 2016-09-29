package com.bitmakersbd.biyebari.server.repository;

import com.bitmakersbd.biyebari.server.model.District;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Long> {
    public Page<District> findAllByIsActive(State isActive, Pageable pageable);
}
