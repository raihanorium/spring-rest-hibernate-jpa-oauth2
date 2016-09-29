package com.bitmakersbd.biyebari.server.controller;

import com.bitmakersbd.biyebari.server.service.AreaService;
import com.bitmakersbd.biyebari.server.service.DistrictService;
import com.bitmakersbd.biyebari.server.util.RestResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for {@link com.bitmakersbd.biyebari.server.model.District}. This manages districts
 * of {@link com.bitmakersbd.biyebari.server.model.Area}.
 *
 * @see com.bitmakersbd.biyebari.server.model.District
 * @see com.bitmakersbd.biyebari.server.model.Area
 * @see com.bitmakersbd.biyebari.server.service.DistrictService
 * @see com.bitmakersbd.biyebari.server.service.DistrictServiceImpl
 * @see com.bitmakersbd.biyebari.server.repository.DistrictRepository
 */
@RestController
@RequestMapping(value = "/districts")
public class DistrictController {
    private static final Logger logger = Logger.getLogger(DistrictController.class);

    @Autowired
    DistrictService districtService;

    @Autowired
    AreaService areaService;

    /**
     * Gets all active districts.
     *
     * @param pageable the page parameters
     * @return all active districts as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getAll(Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(districtService.getAll(pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Gets all area of the district.
     *
     * @param id       the id of the district
     * @param pageable the page parameters
     * @return all active areas of the district as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/areas", method = RequestMethod.GET)
    public RestResponse getAllArea(@PathVariable(value = "id") long id, Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(areaService.getAllByDistrictId(id, pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Get a single district.
     *
     * @param id the id of the district
     * @return the district as {@link com.bitmakersbd.biyebari.server.util.RestResponse}. If the district is not
     * active then the data is null and a message tells that the district is not active.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse get(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(districtService.get(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }
}
