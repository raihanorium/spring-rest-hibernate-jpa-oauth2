package com.bitmakersbd.biyebari.server.service;

import com.bitmakersbd.biyebari.server.model.ApplicationSettings;

public interface ApplicationSettingsService {
    public ApplicationSettings getByKey(String key) throws Exception;
}
