package com.bitmakersbd.biyebari.server.controller;

import com.bitmakersbd.biyebari.server.service.AreaService;
import com.bitmakersbd.biyebari.server.util.RestResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for {@link com.bitmakersbd.biyebari.server.model.Area}. This manages areas
 * of {@link com.bitmakersbd.biyebari.server.model.Vendor}.
 *
 * @see com.bitmakersbd.biyebari.server.model.Vendor
 * @see com.bitmakersbd.biyebari.server.model.District
 * @see com.bitmakersbd.biyebari.server.service.AreaService
 * @see com.bitmakersbd.biyebari.server.service.AreaServiceImpl
 * @see com.bitmakersbd.biyebari.server.repository.AreaRepository
 */
@RestController
@RequestMapping(value = "/areas")
public class AreaController {
    private static final Logger logger = Logger.getLogger(AreaController.class);

    @Autowired
    AreaService areaService;

    /**
     * Gets all active areas.
     *
     * @return all active areas as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getAll() {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(areaService.getAll());
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Get a single area.
     *
     * @param id the id of the area
     * @return the area as {@link com.bitmakersbd.biyebari.server.util.RestResponse}. If the area is not
     * active then the data is null and a message tells that the area is not active.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse get(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(areaService.get(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }
}
