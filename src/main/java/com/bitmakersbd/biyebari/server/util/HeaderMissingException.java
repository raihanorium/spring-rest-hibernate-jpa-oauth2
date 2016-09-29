package com.bitmakersbd.biyebari.server.util;

public class HeaderMissingException extends Exception {
    public HeaderMissingException(String message) {
        super(message);
    }

    public HeaderMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
