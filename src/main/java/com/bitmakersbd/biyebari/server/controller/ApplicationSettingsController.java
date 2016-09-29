package com.bitmakersbd.biyebari.server.controller;

import com.bitmakersbd.biyebari.server.service.ApplicationSettingsService;
import com.bitmakersbd.biyebari.server.util.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for {@link com.bitmakersbd.biyebari.server.model.ApplicationSettings}. This manages
 * application level settings stored in database. The settings are stored as key-value pairs in database.
 *
 * @see com.bitmakersbd.biyebari.server.model.ApplicationSettings
 * @see com.bitmakersbd.biyebari.server.service.ApplicationSettingsService
 * @see com.bitmakersbd.biyebari.server.service.ApplicationSettingsServiceImpl
 * @see com.bitmakersbd.biyebari.server.repository.ApplicationSettingsRepository
 */
@RestController
@RequestMapping(value = "/settings")
public class ApplicationSettingsController {
    @Autowired
    ApplicationSettingsService applicationSettingsService;

    /**
     * Get a setting by key.
     *
     * @param key the key
     * @return the value as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public RestResponse get(@PathVariable(value = "key") String key) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(applicationSettingsService.getByKey(key));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }
}
