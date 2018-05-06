package com.booking.synopsis.client.dto;

import java.util.Collections;

public class VoiceBunnyDtoTestBuilder {

    public VoiceBunnyDto build() {
        return new VoiceBunnyDto(
                new VoiceBunnyProjectDto(
                        Collections.singletonList(
                                new VoiceBunnyReadDto(
                                    new VoiceBunnyReadUrlsDto(
                                        new VoiceBunnyUrlPartDto("https://dropbox.com/sandbox/test.wav")
                                    )
                                )
                        )
                )
        );
    }
}
