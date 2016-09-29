package com.bitmakersbd.biyebari.server.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class RestErrorHandler {
    private MessageSource messageSource;
    private static final Logger logger = Logger.getLogger(RestErrorHandler.class);

    @Autowired
    Messages messages;

    @Autowired
    public RestErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResponse processValidationError(MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage(), ex);

        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        return processFieldErrors(fieldErrors);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public
    @ResponseBody
    RestResponse handleBadRequest(HttpMessageNotReadableException ex) {
        logger.error(ex.getMessage(), ex);

        RestResponse restResponse = new RestResponse();
        restResponse.setError(true);
        restResponse.addMessage(messages.getMessage("exception.bad.request"));

        return restResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public
    @ResponseBody
    RestResponse handleInternalError(Exception ex) {
        logger.error(ex.getMessage(), ex);

        RestResponse restResponse = new RestResponse();
        restResponse.setError(true);
        restResponse.addMessage(messages.getMessage("exception.internal.error"));

        return restResponse;
    }

    @ExceptionHandler(HeaderMissingException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
    public
    @ResponseBody
    RestResponse handleLoggedInUserIdRequired(Exception ex) {
        logger.error(ex.getMessage(), ex);

        RestResponse restResponse = new RestResponse();
        restResponse.setError(true);
        restResponse.addMessage(messages.getMessage("exception.logged.user.header.missing"));

        return restResponse;
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public
    @ResponseBody
    RestResponse handleMediaTypeException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        RestResponse restResponse = new RestResponse();
        restResponse.setError(true);
        restResponse.addMessage(messages.getMessage("exception.media.type.json"));

        return restResponse;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public
    @ResponseBody
    RestResponse handleMissingParameterException(Exception ex) {
        logger.error(ex.getMessage(), ex);

        RestResponse restResponse = new RestResponse();
        restResponse.setError(true);
        restResponse.getMessages().add(ex.getMessage());

        return restResponse;
    }

    private RestResponse processFieldErrors(List<FieldError> fieldErrors) {
        RestResponse restResponse = new RestResponse();
        restResponse.setError(true);

        for (FieldError fieldError : fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            restResponse.getErrorFields().add(fieldError.getField());
            restResponse.getMessages().add(localizedErrorMessage);
        }

        return restResponse;
    }

    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale = LocaleContextHolder.getLocale();
        String localizedErrorMessage = messageSource.getMessage(fieldError, currentLocale);

        if (localizedErrorMessage.equals(fieldError.getDefaultMessage())) {
            String[] fieldErrorCodes = fieldError.getCodes();
            localizedErrorMessage = fieldErrorCodes[0];
        }

        return localizedErrorMessage;
    }
}
