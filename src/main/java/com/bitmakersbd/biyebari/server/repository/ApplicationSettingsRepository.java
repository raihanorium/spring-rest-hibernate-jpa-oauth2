package com.bitmakersbd.biyebari.server.repository;

import com.bitmakersbd.biyebari.server.model.ApplicationSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationSettingsRepository extends JpaRepository<ApplicationSettings, String> {
}
