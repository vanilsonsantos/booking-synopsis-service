package com.booking.synopsis.response;

import com.booking.synopsis.validation.ValidationError;

import java.util.Collections;
import java.util.List;

public class SynopsisErrorResponseTestBuilder {

    private int code = 500;
    private String message = "Error when booking a new synopsis";
    private List<ValidationError> errors = Collections.singletonList(
            new ValidationError("movieName", "invalid movieName")
    );

    public SynopsisErrorResponseTestBuilder withErrors(List<ValidationError> errors) {
        this.errors = errors;
        return this;
    }

    public SynopsisErrorResponse build() {
        return new SynopsisErrorResponse(
                code,
                message,
                errors
        );
    }
}
