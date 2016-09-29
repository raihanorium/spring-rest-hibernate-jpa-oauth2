package com.bitmakersbd.biyebari.server.controller;

import com.bitmakersbd.biyebari.server.model.Category;
import com.bitmakersbd.biyebari.server.service.CategoryService;
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
 * REST API controller for {@link com.bitmakersbd.biyebari.server.model.Category}. This manages categories
 * of {@link com.bitmakersbd.biyebari.server.model.Item}.
 *
 * @see com.bitmakersbd.biyebari.server.model.Category
 * @see com.bitmakersbd.biyebari.server.service.CategoryService
 * @see com.bitmakersbd.biyebari.server.service.CategoryServiceImpl
 * @see com.bitmakersbd.biyebari.server.repository.CategoryRepository
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
    private static final Logger logger = Logger.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    Messages messages;

    /**
     * Gets all of the categories which are active. The paging support is removed and all of the categories
     * are returned in a single page.
     *
     * @return all of the active categories as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getAll() {
        RestResponse restResponse = new RestResponse();
        try {
            // no pageable. because we want all of the categories in a single page.
            restResponse.setData(categoryService.getAll());
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Gets all leaf categories who has no child.
     *
     * @param pageable the page parameters
     * @return all of the leaf categories as {@link com.bitmakersbd.biyebari.server.util.RestResponse} which are active.
     */
    @RequestMapping(value = "/leaves", method = RequestMethod.GET)
    public RestResponse getAllLeaves(Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(categoryService.getAllLeaves(pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Get a single category by id.
     *
     * @param id the id of the category
     * @return the category as {@link com.bitmakersbd.biyebari.server.util.RestResponse}.
     * If the category is not active, then the data will be null and a message will tell that the category is not
     * in active state.
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse get(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(categoryService.get(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Create a new category.
     *
     * @param category the category
     * @return the created category with id as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestResponse create(@Validated(ValidateOnCreate.class) @RequestBody Category category) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(categoryService.create(category));
            restResponse.addMessage(messages.getMessage("created.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Update category.
     *
     * @param category the category
     * @param id       the id of the category
     * @return the updated category as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse update(@Validated(ValidateOnUpdate.class) @RequestBody Category category, @PathVariable(value = "id") Long id) {
        RestResponse restResponse = new RestResponse();
        try {
            category.setId(id);
            restResponse.setData(categoryService.update(category));
            restResponse.addMessage(messages.getMessage("updated.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Delete a category.
     *
     * @param id the id
     * @return the deleted category as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            categoryService.delete(id);
            restResponse.setData(true);
            restResponse.addMessage(messages.getMessage("deleted.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Gets all active children of a category.
     *
     * @param id       the id of the category
     * @param pageable the page parameters
     * @return all active child categories as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/children", method = RequestMethod.GET)
    public RestResponse getAllChildren(@PathVariable(value = "id") long id, Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(categoryService.getAllChildren(id, pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Gets all active items of a category.
     *
     * @param id       the id of the category
     * @param pageable the page parameters
     * @return all active items of the category as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/items", method = RequestMethod.GET)
    public RestResponse getAllItems(@PathVariable(value = "id") long id, Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(categoryService.getAllItems(id, pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }
}
