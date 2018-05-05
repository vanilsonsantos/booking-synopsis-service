package com.booking.synopsis.service;

import com.booking.synopsis.client.OmdbClient;
import com.booking.synopsis.client.VoiceBunnyClient;
import com.booking.synopsis.response.Synopsis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingSynopsisService {

    private OmdbClient omdbClient;
    private VoiceBunnyClient voiceBunnyClient;

    @Autowired
    public BookingSynopsisService(OmdbClient omdbClient, VoiceBunnyClient voiceBunnyClient) {
        this.omdbClient = omdbClient;
        this.voiceBunnyClient = voiceBunnyClient;
    }

    public Synopsis save(String movieName) {
        String synopsis = omdbClient.getSynopsis(movieName);
        String audioLink = voiceBunnyClient.bookSynopsis(synopsis);
        return new Synopsis(
                synopsis,
                audioLink
        );
    }
}
