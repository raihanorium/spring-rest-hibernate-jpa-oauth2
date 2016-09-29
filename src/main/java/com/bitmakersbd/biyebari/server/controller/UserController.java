package com.bitmakersbd.biyebari.server.controller;

import com.bitmakersbd.biyebari.server.model.User;
import com.bitmakersbd.biyebari.server.service.EmailService;
import com.bitmakersbd.biyebari.server.service.UserService;
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

/**
 * REST API controller for {@link com.bitmakersbd.biyebari.server.model.User}. This manages users.
 *
 * @see com.bitmakersbd.biyebari.server.model.User
 * @see com.bitmakersbd.biyebari.server.service.UserService
 * @see com.bitmakersbd.biyebari.server.service.UserServiceImpl
 * @see com.bitmakersbd.biyebari.server.repository.UserRepository
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    EmailService emailService;

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
     * Gets all users.
     *
     * @param pageable the page parameters
     * @return all users as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.GET)
    public RestResponse getAll(Pageable pageable) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(userService.getAll(pageable));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Search users with first or last name.
     *
     * @param keyword  the keyword to search with
     * @param pageable the page parameters
     * @return the matched users as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public RestResponse search(@PathVariable(value = "keyword") String keyword, Pageable pageable) {
        RestResponse restResponse = new RestResponse();

        logger.info("Searching with " + keyword);
        restResponse.setData(userService.search(keyword, pageable));

        return restResponse;
    }

    /**
     * Get a user.
     *
     * @param id the id of the user
     * @return the user as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestResponse get(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(userService.get(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Create a user.
     *
     * @param user the user object
     * @return the created user as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestResponse create(@Validated(ValidateOnCreate.class) @RequestBody User user) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(userService.create(user));
            restResponse.addMessage(messages.getMessage("created.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Update a user.
     *
     * @param user the user object to update
     * @param id   the id of the user
     * @return the updated user as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestResponse update(@Validated(ValidateOnUpdate.class) @RequestBody User user, @PathVariable(value = "id") Long id) {
        RestResponse restResponse = new RestResponse();
        try {
            user.setId(id);
            restResponse.setData(userService.update(user));
            restResponse.addMessage(messages.getMessage("updated.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Delete a user.
     *
     * @param id the id of the user
     * @return the deleted user as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestResponse delete(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            userService.delete(id);
            restResponse.setData(true);
            restResponse.addMessage(messages.getMessage("deleted.successfully"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Login user.
     *
     * @param user     the email or mobile number of the user
     * @param password the password of the user
     * @return the user object as {@link com.bitmakersbd.biyebari.server.util.RestResponse} if password is correct
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public RestResponse login(@RequestParam String user, @RequestParam String password) {
        // user is the email or contact number of the user
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(userService.login(user, password));
            restResponse.addMessage(messages.getMessage("login.success"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Upload user profile pic. Uploading a profile pic will discard the previous picture.
     *
     * @param id                  the id of the vendor
     * @param singleFileContainer the picture file to upload as {@link com.bitmakersbd.biyebari.server.util.SingleFileContainer}
     * @return the updated user object as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/uploadProfilePic", method = RequestMethod.POST)
    public RestResponse uploadProfilePic(@PathVariable(value = "id") long id, @Valid SingleFileContainer singleFileContainer, BindingResult result) throws IOException {
        RestResponse restResponse = new RestResponse();

        if (checkValidationErrors(result, restResponse)) return restResponse;

        try {
            userService.updateProfilePic(id, singleFileContainer.getFile());
            restResponse.setData(userService.get(id));
            restResponse.addMessage(messages.getMessage("file.upload.success"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }

        return restResponse;
    }

    /**
     * Change password of the user.
     *
     * @param id          the id of the user
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return the message as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public RestResponse changePassword(@PathVariable(value = "id") long id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(userService.changePassword(id, oldPassword, newPassword));
            restResponse.addMessage(messages.getMessage("password.changed"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Reset user password and send password to user's email.
     *
     * @param email the email of the user
     * @return the user object as {@link com.bitmakersbd.biyebari.server.util.RestResponse} if password is correct
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public RestResponse resetPassword(@RequestParam String email) {
        // user is the email of the user
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.setData(userService.resetPassword(email));
            restResponse.addMessage(messages.getMessage("user.password.reset.link.sent"));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }


    /**
     * Generates a verification code
     *
     * @param id the id of the user
     * @return the user as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/sendVerificationCode/{id}", method = RequestMethod.GET)
    public RestResponse generateVerificationCode(@PathVariable(value = "id") long id) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.addMessage(messages.getMessage("sms.sent"));
            restResponse.setData(userService.sendVerificationCode(id));
        } catch (Exception e) {
            restResponse.setError(true);
            restResponse.addMessage(e.getMessage());
        }
        return restResponse;
    }

    /**
     * Verifies user verification code
     *
     * @param id   the id of the user
     * @param code the verification code
     * @return the user as {@link com.bitmakersbd.biyebari.server.util.RestResponse}
     */
    @RequestMapping(value = "/verifyCode/{id}/{code}", method = RequestMethod.GET)
    public RestResponse generateVerificationCode(@PathVariable(value = "id") long id, @PathVariable(value = "code") Integer code) {
        RestResponse restResponse = new RestResponse();
        try {
            restResponse.addMessage(messages.getMessage("verified.successfully"));
            restResponse.setData(userService.verifyCode(id, code));
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
