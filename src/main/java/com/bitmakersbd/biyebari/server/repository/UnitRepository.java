package com.bitmakersbd.biyebari.server.repository;

import com.bitmakersbd.biyebari.server.model.Unit;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    public Page<Unit> findAllByIsActive(State isActive, Pageable pageable);
}
