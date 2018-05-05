package com.booking.synopsis.validation;

public final class ValidationMessages {

    public static final String INVALID_MOVIE_NAME_MESSAGE = "The name of the movie should not be null or empty";

    private ValidationMessages() {
        throw new RuntimeException();
    }
}
