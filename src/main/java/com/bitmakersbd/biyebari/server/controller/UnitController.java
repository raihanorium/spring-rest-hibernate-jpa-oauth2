package com.bitmakersbd.biyebari.server.controller;

import com.bitmakersbd.biyebari.server.model.Unit;
import com.bitmakersbd.biyebari.server.service.UnitService;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.RestResponse;
import com.bitmakersbd.biyebari.server.validation.ValidateOnCreate;
import com.bitmakersbd.biyebari.server.validation.ValidateOnUpdate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST API controller for {@link com.bitmakersbd.biyebari.server.model.Unit}. This manages units of product items.
 * Items are managed by the vendor.
 *
 * @see com.bitmakersbd.biyebari.server.model.Unit
 * @see com.bitmakersbd.biyebari.server.service.UnitService
 * @see com.bitmakersbd.biyebari.server.service.UnitServiceImpl
 * @see com.bitmakersbd.biyebari.server.repository.UnitRepository
 */
@RestController
@RequestMapping(value = "/units")
public class UnitController {
    private static final Logger logger = Logger.getLogger(UnitController.class);

    @Autowired
    UnitService unitService;

    @Autowired
    Messages messages;

    /**
     * Gets all units.
     *
     * @param pageable the page parameters
     * @return the all units as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getAll(Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(unitService.getAll(pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Get a single unit.
     *
     * @param id the id of the unit
     * @return the unit object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse get(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(unitService.get(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Create a new unit
     *
     * @param unit the unit object
     * @return the unit as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestResponse create(@Validated(ValidateOnCreate.class) @RequestBody Unit unit) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(unitService.create(unit));
            restResponse.addMessage(messages.getMessage("created.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Update unit.
     *
     * @param unit the unit object
     * @param id   the id of the unit
     * @return the updated unit object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse update(@Validated(ValidateOnUpdate.class) @RequestBody Unit unit, @PathVariable(value = "id") Long id) {
        RestResponse restResponse = new RestResponse();
        try {
            unit.setId(id);
            restResponse.setData(unitService.update(unit));
            restResponse.addMessage(messages.getMessage("updated.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Delete unit.
     *
     * @param id the id of the unit
     * @return the deleted unit as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            unitService.delete(id);
            restResponse.setData(true);
            restResponse.addMessage(messages.getMessage("deleted.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }
}
