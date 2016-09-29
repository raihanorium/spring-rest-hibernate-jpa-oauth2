package com.bitmakersbd.biyebari.server.controller;

import com.bitmakersbd.biyebari.server.model.Vendor;
import com.bitmakersbd.biyebari.server.service.VendorService;
import com.bitmakersbd.biyebari.server.util.Messages;
import com.bitmakersbd.biyebari.server.util.RestResponse;
import com.bitmakersbd.biyebari.server.util.SingleFileContainer;
import com.bitmakersbd.biyebari.server.validation.SingleFileValidator;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * REST API controller for {@link com.bitmakersbd.biyebari.server.model.Vendor}. This manages vendors.
 *
 * @see com.bitmakersbd.biyebari.server.model.Vendor
 * @see com.bitmakersbd.biyebari.server.service.VendorService
 * @see com.bitmakersbd.biyebari.server.service.VendorServiceImpl
 * @see com.bitmakersbd.biyebari.server.repository.VendorRepository
 */
@RestController
@RequestMapping(value = "/vendors")
public class VendorController {
    private static final Logger logger = Logger.getLogger(VendorController.class);

    @Autowired
    VendorService vendorService;

    @Autowired
    Messages messages;

    @Autowired
    SingleFileValidator singleFileValidator;

    /**
     * Initializes the single file validator
     *
     * @param binder the binder
     */
    @InitBinder("singleFileContainer")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(singleFileValidator);
    }

    /**
     * Gets all vendors.
     *
     * @param pageable the page parameters
     * @return the all vendors as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getAll(Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(vendorService.getAll(pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Get a single vendor.
     *
     * @param id the id of the vendor
     * @return the vendor object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse get(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(vendorService.get(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Create a vendor.
     *
     * @param vendor the vendor object
     * @return the created vendor as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestResponse create(@Validated(ValidateOnCreate.class) @RequestBody Vendor vendor) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(vendorService.create(vendor));
            restResponse.addMessage(messages.getMessage("created.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Update a vendor.
     *
     * @param vendor the vendor to update
     * @param id     the id of the vendor
     * @return the updated vendor as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse update(@Validated(ValidateOnUpdate.class) @RequestBody Vendor vendor, @PathVariable(value = "id") Long id) {
        RestResponse restResponse = new RestResponse();
        try {
            vendor.setId(id);
            restResponse.setData(vendorService.update(vendor));
            restResponse.addMessage(messages.getMessage("updated.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Delete a vendor.
     *
     * @param id the id of the vendor
     * @return the deleted vendor as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            vendorService.delete(id);
            restResponse.setData(true);
            restResponse.addMessage(messages.getMessage("deleted.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Login vendor.
     *
     * @param user     the email or mobile number of the vendor
     * @param password the password
     * @return the vendor object as {@link com.bitmakersbd.biyebari.server.util.RestResponse} if password is correct
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse login(@RequestParam String user, @RequestParam String password) {
        // user is the email or contact number of the user
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(vendorService.login(user, password));
            restResponse.addMessage(messages.getMessage("login.success"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Upload company logo. Uploading a logo will discard the previous logo.
     *
     * @param id                  the id of the vendor
     * @param singleFileContainer the logo file to upload as {@link com.bitmakersbd.biyebari.server.util.SingleFileContainer}
     * @return the updated vendor object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/uploadLogo", method = RequestMethod.POST)
    public RestResponse uploadLogo(@PathVariable(value = "id") long id, @Valid SingleFileContainer singleFileContainer, BindingResult result) throws IOException {
        RestResponse restResponse = new RestResponse();

        if (checkValidationErrors(result, restResponse)) return restResponse;

        try {
            vendorService.updateLogo(id, singleFileContainer.getFile());
            restResponse.setData(vendorService.get(id));
            restResponse.addMessage(messages.getMessage("file.upload.success"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }

        return restResponse;
    }

    /**
     * Update areas of the vendor. Areas can be updated by vendor update method, this is a simple extension of that.
     *
     * @param id    the id of the vendor
     * @param areas the areas as integer array
     * @return the updated vendor object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/updateAreas", method = RequestMethod.PATCH)
    public RestResponse updateAreas(@PathVariable(value = "id") Long id, @RequestBody Set<Long> areas) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(vendorService.updateAreas(id, areas));
            restResponse.addMessage(messages.getMessage("updated.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Update categories of the vendor. Categories can be updated by vendor update method, this is a simple
     * extension of that.
     *
     * @param id         the id of the vendor
     * @param categories the categories as integer array
     * @return the updated vendor object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/updateCategories", method = RequestMethod.PATCH)
    public RestResponse updateCategories(@PathVariable(value = "id") Long id, @RequestBody Set<Long> categories) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(vendorService.updateCategories(id, categories));
            restResponse.addMessage(messages.getMessage("updated.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Gets all items created by the vendor which are active.
     *
     * @param id       the id of the vendor
     * @param pageable the page parameters
     * @return all active and inactive items of the vendor as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/items", method = RequestMethod.GET)
    public RestResponse getAllItems(@PathVariable(value = "id") Long id, Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(vendorService.getAllItemsByVendor(id, pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Change password of vendor.
     *
     * @param id          the id of the vendor
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return the message as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public RestResponse changePassword(@PathVariable(value = "id") long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(vendorService.changePassword(id, oldPassword, newPassword));
            restResponse.addMessage(messages.getMessage("password.changed"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Generates a verification code
     *
     * @param id the id of the vendor
     * @return the vendor as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/sendVerificationCode/{id}", method = RequestMethod.GET)
    public RestResponse generateVerificationCode(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.addMessage(messages.getMessage("sms.sent"));
            restResponse.setData(vendorService.sendVerificationCode(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Verifies vendor verification code
     *
     * @param id   the id of the vendor
     * @param code the verification code
     * @return the vendor as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/verifyCode/{id}/{code}", method = RequestMethod.GET)
    public RestResponse generateVerificationCode(@PathVariable(value = "id") long id, @PathVariable(value = "code") Integer code) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.addMessage(messages.getMessage("verified.successfully"));
            restResponse.setData(vendorService.verifyCode(id, code));
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
}
