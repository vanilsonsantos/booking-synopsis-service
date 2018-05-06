package com.booking.synopsis.client;

import com.booking.synopsis.client.dto.MovieDbDto;
import com.booking.synopsis.client.dto.MovieDbResultDto;
import com.booking.synopsis.exceptions.ExternalServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MovieDbClient {

    private String apiKey;
    private String host;
    private final String URL = "/3/search/movie?api_key=%s&query=%s";
    private RestTemplate restTemplate;

    @Autowired
    public MovieDbClient(RestTemplate restTemplate,
                         @Value("${moviedb.host}") String host,
                         @Value("${moviedb.api.key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.host = host;
        this.apiKey = apiKey;
    }

    public String getSynopsis(String expectedMovieName) throws ExternalServiceException {
        String requestUrl = String.format(host.concat(URL), apiKey, expectedMovieName);

        try {
            HttpEntity<MovieDbDto> response = restTemplate.getForEntity(requestUrl, MovieDbDto.class);
            return getOverview(response.getBody().getResults());
        } catch (Exception ex) {
            throw new ExternalServiceException(ex);
        }
    }

    private String getOverview(List<MovieDbResultDto> results) throws ExternalServiceException {
        if (results.isEmpty()) {
            throw new ExternalServiceException("Movie not found, please type another one.");
        }
        return results.get(0).getOverview();
    }
}
