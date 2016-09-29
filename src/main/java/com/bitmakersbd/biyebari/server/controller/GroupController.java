package com.bitmakersbd.biyebari.server.controller;

import com.bitmakersbd.biyebari.server.model.Group;
import com.bitmakersbd.biyebari.server.service.GroupService;
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
 * REST API controller for {@link com.bitmakersbd.biyebari.server.model.Group}. This manages groups.
 *
 * @see com.bitmakersbd.biyebari.server.model.Group
 * @see com.bitmakersbd.biyebari.server.service.GroupService
 * @see com.bitmakersbd.biyebari.server.service.GroupServiceImpl
 * @see com.bitmakersbd.biyebari.server.repository.GroupRepository
 */
@RestController
@RequestMapping(value = "/groups")
public class GroupController {
    private static final Logger logger = Logger.getLogger(GroupController.class);

    @Autowired
    GroupService groupService;

    @Autowired
    Messages messages;

    /**
     * Gets all active groups.
     *
     * @param pageable the page parameters
     * @return all active groups as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getAll(Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(groupService.getAll(pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Get a single group with id.
     *
     * @param id the id of the group
     * @return the group as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse get(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(groupService.get(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Create a group.
     *
     * @param group the group object
     * @return the created group as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestResponse create(@Validated(ValidateOnCreate.class) @RequestBody Group group) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(groupService.create(group));
            restResponse.addMessage(messages.getMessage("created.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Update a group.
     *
     * @param group the group to update
     * @param id    the id of the group
     * @return the updated group as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse update(@Validated(ValidateOnUpdate.class) @RequestBody Group group, @PathVariable(value = "id") Long id) {
        RestResponse restResponse = new RestResponse();
        try {
            group.setId(id);
            restResponse.setData(groupService.update(group));
            restResponse.addMessage(messages.getMessage("updated.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Delete a group.
     *
     * @param id the id of the group
     * @return the deleted group as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            groupService.delete(id);
            restResponse.setData(true);
            restResponse.addMessage(messages.getMessage("deleted.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }
}
