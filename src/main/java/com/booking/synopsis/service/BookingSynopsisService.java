package com.booking.synopsis.service;

import com.booking.synopsis.client.MovieDbClient;
import com.booking.synopsis.client.VoiceBunnyClient;
import com.booking.synopsis.exceptions.ExternalServiceException;
import com.booking.synopsis.response.Synopsis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingSynopsisService {

    private MovieDbClient movieDbClient;
    private VoiceBunnyClient voiceBunnyClient;

    @Autowired
    public BookingSynopsisService(MovieDbClient movieDbClient, VoiceBunnyClient voiceBunnyClient) {
        this.movieDbClient = movieDbClient;
        this.voiceBunnyClient = voiceBunnyClient;
    }

    public Synopsis save(String movieName) throws ExternalServiceException {
        String synopsis = movieDbClient.getSynopsis(movieName);
        String audioLink = voiceBunnyClient.bookSynopsis(synopsis);
        return new Synopsis(
                synopsis,
                audioLink
        );
    }
}
