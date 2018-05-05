package com.booking.synopsis.service;

import com.booking.synopsis.client.OmdbClient;
import com.booking.synopsis.client.VoiceBunnyClient;
import com.booking.synopsis.response.Synopsis;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BookingSynopsisServiceTest {

    @Mock
    private OmdbClient omdbClient;

    @Mock
    private VoiceBunnyClient voiceBunnyClient;

    private BookingSynopsisService bookingSynopsisService;

    @Before
    public void setUp() {
        initMocks(this);
        bookingSynopsisService = new BookingSynopsisService(omdbClient, voiceBunnyClient);
    }

    @Test
    public void shouldSaveAndReturnTheBookedSynopsis() {
        //given
        String expectedMovieName = "child's play";
        String expectedSynopsis = "This is my synopsis";
        String expectedAudioLink = "https://voicebunny.s3.amazonaws.com/sandbox/low_test.mp3";
        when(omdbClient.getSynopsis(expectedMovieName)).thenReturn(expectedSynopsis);
        when(voiceBunnyClient.bookSynopsis(expectedSynopsis)).thenReturn(expectedAudioLink);

        //when
        Synopsis synopsis = bookingSynopsisService.save(expectedMovieName);

        //then
        assertThat(synopsis.getSynopsis(), is(expectedSynopsis));
        assertThat(synopsis.getAudioLink(), is(expectedAudioLink));
    }
}