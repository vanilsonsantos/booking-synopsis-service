package com.booking.synopsis.client;

import com.booking.synopsis.client.dto.MovieDbDto;
import com.booking.synopsis.client.dto.MovieDbDtoTestBuilder;
import com.booking.synopsis.exceptions.ExternalServiceException;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.booking.synopsis.utils.JsonUtils.asJsonString;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MovieDbClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(4444);

    private MovieDbDtoTestBuilder movieDbDtoTestBuilder;

    private MovieDbClient movieDbClient;
    private final String API_KEY = "test-key";
    private final String HOST = "http://localhost:4444";
    private String URL = "/3/search/movie?api_key=%s&query=%s";

    @Before
    public void setUp() {
        movieDbClient = new MovieDbClient(new RestTemplate(), HOST, API_KEY);
        movieDbDtoTestBuilder = new MovieDbDtoTestBuilder();
    }

    @Test
    public void shouldReturnOverview() throws ExternalServiceException {
        //given
        String expectedMovieName = "Robocop";
        String url = String.format(URL, API_KEY, expectedMovieName);
        MovieDbDto movieDbDto = movieDbDtoTestBuilder.build();
        configureStub(url, asJsonString(movieDbDto), 200);

        //when
        String overview = movieDbClient.getSynopsis(expectedMovieName);

        //then
        assertThat(overview, is(movieDbDto.getResults().get(0).getOverview()));
    }

    @Test(expected = ExternalServiceException.class)
    public void shouldThrowExceptionIfClientDoesNotReturnAnyOverview() throws ExternalServiceException {
        //given
        String expectedMovieName = "Crazy-not-found-movie";
        String url = String.format(URL, API_KEY, expectedMovieName);
        MovieDbDto movieDbDto = movieDbDtoTestBuilder.withResults(Collections.emptyList()).build();
        configureStub(url, asJsonString(movieDbDto), 200);

        //when
        movieDbClient.getSynopsis(expectedMovieName);
    }

    @Test(expected = ExternalServiceException.class)
    public void shouldThrowExceptionIfClientReturnAnyHttpStatusCode() throws ExternalServiceException {
        //given
        String expectedMovieName = "Robocop";
        String url = String.format(URL, API_KEY, expectedMovieName);
        MovieDbDto movieDbDto = movieDbDtoTestBuilder.build();
        configureStub(url, asJsonString(movieDbDto), 500);

        //when
        movieDbClient.getSynopsis(expectedMovieName);
    }

    private void configureStub(String url, String response, int httpStatus) {
        stubFor(
                get(urlPathEqualTo(url))
                        .willReturn(aResponse()
                                .withStatus(httpStatus)
                                .withBody(response)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        )
        );
    }
}