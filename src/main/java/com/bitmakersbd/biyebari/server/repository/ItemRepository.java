package com.bitmakersbd.biyebari.server.repository;

import com.bitmakersbd.biyebari.server.model.Item;
import com.bitmakersbd.biyebari.server.model.Vendor;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public Page<Item> findAllByIsActiveIn(Collection<State> isActive, Pageable pageable);

    public Page<Item> findAllByCreatedByAndIsActiveIn(Vendor createdBy, Collection<State> isActive, Pageable pageable);

    public Page<Item> findAllByCategoryIdAndIsActive(Long categoryId, State isActive, Pageable pageable);
}
