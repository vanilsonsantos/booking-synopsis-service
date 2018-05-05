package com.booking.synopsis.response;

public class SynopsisTestBuilder {

    private String synopsis = "This is a story of a guy that wanted to be a developer at Torre.co";
    private String audio_link = "https://voicebunny.s3.amazonaws.com/sandbox/low_test.mp3";

    public Synopsis build() {
        return new Synopsis(
                synopsis,
                audio_link
        );
    }

}
