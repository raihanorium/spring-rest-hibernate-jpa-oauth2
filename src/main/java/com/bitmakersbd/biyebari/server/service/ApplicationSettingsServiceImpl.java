package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.ApplicationSettings;
import com.bitmakersbd.biyebari.server.repository.ApplicationSettingsRepository;
import com.bitmakersbd.biyebari.server.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ApplicationSettingsServiceImpl implements ApplicationSettingsService {
    @Autowired
    ApplicationSettingsRepository applicationSettingsRepository;

    @Autowired
    Messages messages;

    @Transactional(readOnly = true)
    @Override
    public ApplicationSettings getByKey(String key) throws Exception {
        ApplicationSettings settings = applicationSettingsRepository.findOne(key);
        if (settings == null) {
            throw new Exception(messages.getMessage("setting.not.found"));
        }

        return settings;
    }
}
