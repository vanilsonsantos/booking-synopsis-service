package com.booking.synopsis.client;

import com.booking.synopsis.client.dto.VoiceBunnyDto;
import com.booking.synopsis.exceptions.ExternalServiceException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class VoiceBunnyClient {

    private String user;
    private String apiKey;
    private String host;
    private static final String URL = "/projects/addBooking";
    private RestTemplate restTemplate;
    private static final String TITLE = "Vanilson - Job Application for Bunny Inc.";
    private static final String TALENT_ID = "81SQO";
    private static final String TEST = "2";

    public VoiceBunnyClient(RestTemplate restTemplate,
                            @Value("${voicebunny.host}") String host,
                            @Value("${voicebunny.api.user}") String user,
                            @Value("${voicebunny.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.host = host;
        this.user = user;
        this.apiKey = apiKey;
    }

    public String bookSynopsis(String expectedSynopsis) throws ExternalServiceException {
        String requestUrl = String.format(host.concat(URL));
        HttpHeaders headers = new HttpHeaders();
        String userAndPass = user.concat(":").concat(apiKey);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.AUTHORIZATION, "Basic ".concat(Base64.encodeBase64String(userAndPass.getBytes())));
        BookSynopsisBody bookSynopsisBody = new BookSynopsisBody(expectedSynopsis, TITLE, TEST, TALENT_ID);
        HttpEntity<BookSynopsisBody> httpEntity = new HttpEntity<>(bookSynopsisBody, headers);

        try {
            ResponseEntity<VoiceBunnyDto> response = restTemplate.postForEntity(requestUrl, httpEntity, VoiceBunnyDto.class);
            return response.getBody().getProject().getReads().get(0).getUrls().getPart001().getOriginal();
        } catch (Exception ex) {
            throw new ExternalServiceException(ex);
        }
    }
}
