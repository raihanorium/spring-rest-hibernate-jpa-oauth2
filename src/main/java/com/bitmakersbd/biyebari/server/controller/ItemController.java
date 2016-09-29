package com.bitmakersbd.biyebari.server.controller;

import com.bitmakersbd.biyebari.server.model.Item;
import com.bitmakersbd.biyebari.server.service.ItemService;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.MultipleFileContainer;
import com.bitmakersbd.biyebari.server.util.RestResponse;
import com.bitmakersbd.biyebari.server.util.State;
import com.bitmakersbd.biyebari.server.validation.MultipleFileValidator;
import com.bitmakersbd.biyebari.server.validation.ValidateOnCreate;
import com.bitmakersbd.biyebari.server.validation.ValidateOnUpdate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * REST API controller for {@link com.bitmakersbd.biyebari.server.model.Item}. This manages product items.
 * Items are managed by the vendor.
 *
 * @see com.bitmakersbd.biyebari.server.model.Item
 * @see com.bitmakersbd.biyebari.server.service.ItemService
 * @see com.bitmakersbd.biyebari.server.service.ItemServiceImpl
 * @see com.bitmakersbd.biyebari.server.repository.ItemRepository
 */
@RestController
@RequestMapping(value = "/items")
public class ItemController {
    private static final Logger logger = Logger.getLogger(ItemController.class);

    @Autowired
    ItemService itemService;

    @Autowired
    Messages messages;

    @Autowired
    MultipleFileValidator multipleFileValidator;

    /**
     * Initializes the multiple file upload validator
     *
     * @param binder the binder
     */
    @InitBinder("multipleFileContainer")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(multipleFileValidator);
    }

    /**
     * Gets all active items.
     *
     * @param pageable the page parameters
     * @param state    the state of the items. it can be "active" or "all". the default is "active".
     * @return all active items if no state parameter is given or the state parameter is "active".
     * otherwise, if the state parameter is "all", then active and inactive items as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getAll(Pageable pageable, @RequestParam(defaultValue = "active") String state) {
        RestResponse restResponse = new RestResponse();
        try {
            Collection<State> states = getStatesEnum(state);
            restResponse.setData(itemService.getAll(states, pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Get a single item by id
     *
     * @param id the id of the item
     * @return the item object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse get(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(itemService.get(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Create an item.
     *
     * @param item the item object
     * @return the created item as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestResponse create(@Validated(ValidateOnCreate.class) @RequestBody Item item) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(itemService.create(item));
            restResponse.addMessage(messages.getMessage("created.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Update an item
     *
     * @param item the item object to update
     * @param id   the id of the item
     * @return the updated item as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse update(@Validated(ValidateOnUpdate.class) @RequestBody Item item, @PathVariable(value = "id") Long id) {
        RestResponse restResponse = new RestResponse();
        try {
            item.setId(id);
            itemService.update(item);
            restResponse.setData(itemService.get(id));
            restResponse.addMessage(messages.getMessage("updated.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Delete an item.
     *
     * @param id the id of the item to delete
     * @return the deleted item as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            itemService.delete(id);
            restResponse.setData(true);
            restResponse.addMessage(messages.getMessage("deleted.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Upload image for the item. An item can have multiple images. This upload will remove all of the previous images
     * associated with the item.
     * <p/>
     * UPDATE: Uploading a new image will not delete previous images. Rather, the new image will be added with the existing.
     *
     * @param id                    the id of the item
     * @param multipleFileContainer the images as {@link com.bitmakersbd.biyebari.server.util.MultipleFileContainer}
     * @return the updated item object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/uploadImage", method = RequestMethod.POST)
    public RestResponse uploadImage(@PathVariable(value = "id") Long id, @Valid MultipleFileContainer multipleFileContainer, BindingResult result) {
        RestResponse restResponse = new RestResponse();

        if (checkValidationErrors(result, restResponse)) return restResponse;

        try {
            itemService.addImages(id, multipleFileContainer.getFile());
            restResponse.setData(itemService.get(id));
            restResponse.addMessage(messages.getMessage("file.upload.success"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }

        return restResponse;
    }

    /**
     * Delete image for the item.
     *
     * @param id       the id of the item
     * @param imageIds the ids of the images to delete
     * @return the updated item object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/deleteImages", method = RequestMethod.POST)
    public RestResponse deleteImages(@PathVariable(value = "id") Long id, @RequestBody List<Long> imageIds) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(itemService.deleteImages(id, imageIds));
            restResponse.addMessage(messages.getMessage("deleted.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    private boolean checkValidationErrors(BindingResult result, RestResponse restResponse) {
        List<FieldError> errors = result.getFieldErrors();
        List<String> errorFields = new ArrayList<>();
        List<String> errorMessages = new ArrayList<>();
        for (FieldError error : errors) {
            restResponse.setError(true);
            errorFields.add(error.getField());
            errorMessages.add(error.getCode());
        }
        if (restResponse.isError()) {
            restResponse.setErrorFields(errorFields);
            restResponse.setMessages(errorMessages);
            return true;
        }
        return false;
    }

    private List<State> getStatesEnum(String state) throws Exception {
        List<State> states;
        state = state.toLowerCase().trim();

        switch (state) {
            case "active":
                states = Arrays.asList(State.active);
                break;
            case "all":
                states = Arrays.asList(State.active, State.inactive);
                break;
            default:
                throw new Exception(messages.getMessage("state.active.or.all"));
        }

        return states;
    }
}
