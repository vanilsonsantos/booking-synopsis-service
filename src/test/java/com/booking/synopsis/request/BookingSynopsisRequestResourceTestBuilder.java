package com.booking.synopsis.request;

public class BookingSynopsisRequestResourceTestBuilder {

    private String movieName = "Robocop 2";

    public BookingSynopsisRequestResource build() {
        return new BookingSynopsisRequestResource(movieName);
    }

    public BookingSynopsisRequestResourceTestBuilder withMovieName(String movieName) {
        this.movieName = movieName;
        return this;
    }
}
