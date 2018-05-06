package com.booking.synopsis.client.dto;

import java.util.Collections;
import java.util.List;

public class MovieDbDtoTestBuilder {

    private List<MovieDbResultDto> results = Collections.singletonList(
            new MovieDbResultDto("A story of a guy that wanted to be a developer at Torre.co")
    );

    public MovieDbDtoTestBuilder withResults(List<MovieDbResultDto> results) {
        this.results = results;
        return this;
    }

    public MovieDbDto build() {
        return new MovieDbDto(results);
    }

}
