package com.booking.synopsis.exceptions;

public class ExternalServiceException extends Exception {

    public ExternalServiceException(String message) {
        super(message, new Throwable(message));
    }

    public ExternalServiceException(Throwable throwable) {
        super(throwable);
    }
}
