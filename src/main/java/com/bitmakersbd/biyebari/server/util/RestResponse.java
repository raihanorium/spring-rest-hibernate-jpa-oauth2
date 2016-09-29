package com.bitmakersbd.biyebari.server.util;

import java.util.ArrayList;
import java.util.List;

public class RestResponse {
    private boolean error = false;
    private List<String> errorFields = new ArrayList<>();
    private List<String> messages = new ArrayList<>();
    Object data = null;

    public RestResponse() {}

    public RestResponse(Object data) {
        this.data = data;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<String> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(List<String> errorFields) {
        this.errorFields = errorFields;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String errorMessage) {
        this.messages.add(errorMessage);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
