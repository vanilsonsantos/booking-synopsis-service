package com.booking.synopsis.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import static com.booking.synopsis.validation.ValidationMessages.INVALID_MOVIE_NAME_MESSAGE;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingSynopsisRequestResource implements RequestResource{

    @NotBlank(message = INVALID_MOVIE_NAME_MESSAGE)
    private String movieName;

}
