package com.bitmakersbd.biyebari.server.repository;

import com.bitmakersbd.biyebari.server.model.Category;
import com.bitmakersbd.biyebari.server.util.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    public List<Category> findAllByIsActive(State isActive);

    @Query(value = "SELECT c FROM Category c WHERE c.id NOT IN (SELECT DISTINCT cat.parent FROM Category cat WHERE cat.parent IS NOT NULL OR cat.parent <> 0) AND c.isActive=?1 ORDER BY c.name")
    public Page findAllLeaves(State isActive, Pageable pageable);

    public Page<Category> findAllByParentAndIsActive(Long parent, State isActive, Pageable pageable);
}
