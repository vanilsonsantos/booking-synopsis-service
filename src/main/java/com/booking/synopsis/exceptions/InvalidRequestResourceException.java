package com.booking.synopsis.exceptions;

import com.booking.synopsis.validation.ValidationError;
import lombok.Getter;

import java.util.List;

@Getter
public class InvalidRequestResourceException extends Exception {

    private List<ValidationError> errors;
    private String message;

    public InvalidRequestResourceException(List<ValidationError> errors, String message) {
        super(message);
        this.errors = errors;
        this.message = message;
    }
}
