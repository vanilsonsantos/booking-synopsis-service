package com.booking.synopsis.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Synopsis {

    private String synopsis;

    @JsonProperty("audio_link")
    private String audioLink;

}
