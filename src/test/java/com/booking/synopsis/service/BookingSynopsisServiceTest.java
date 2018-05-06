package com.booking.synopsis.service;

import com.booking.synopsis.client.MovieDbClient;
import com.booking.synopsis.client.VoiceBunnyClient;
import com.booking.synopsis.exceptions.ExternalServiceException;
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
    private MovieDbClient movieDbClient;

    @Mock
    private VoiceBunnyClient voiceBunnyClient;

    private BookingSynopsisService bookingSynopsisService;

    @Before
    public void setUp() {
        initMocks(this);
        bookingSynopsisService = new BookingSynopsisService(movieDbClient, voiceBunnyClient);
    }

    @Test
    public void shouldSaveAndReturnTheBookedSynopsis() throws ExternalServiceException {
        //given
        String expectedMovieName = "child's play";
        String expectedSynopsis = "This is my synopsis";
        String expectedAudioLink = "https://voicebunny.s3.amazonaws.com/sandbox/low_test.mp3";
        when(movieDbClient.getSynopsis(expectedMovieName)).thenReturn(expectedSynopsis);
        when(voiceBunnyClient.bookSynopsis(expectedSynopsis)).thenReturn(expectedAudioLink);

        //when
        Synopsis synopsis = bookingSynopsisService.save(expectedMovieName);

        //then
        assertThat(synopsis.getSynopsis(), is(expectedSynopsis));
        assertThat(synopsis.getAudioLink(), is(expectedAudioLink));
    }
}