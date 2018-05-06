package com.booking.synopsis.client;

import com.booking.synopsis.client.dto.VoiceBunnyDto;
import com.booking.synopsis.client.dto.VoiceBunnyDtoTestBuilder;
import com.booking.synopsis.exceptions.ExternalServiceException;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static com.booking.synopsis.utils.JsonUtils.asJsonString;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class VoiceBunnyClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(4444);

    private VoiceBunnyClient voiceBunnyClient;
    private VoiceBunnyDtoTestBuilder voiceBunnyDtoTestBuilder;

    @Before
    public void setUp() {
        voiceBunnyClient = new VoiceBunnyClient(new RestTemplate(), "http://localhost:4444", "user", "apikey");
        voiceBunnyDtoTestBuilder = new VoiceBunnyDtoTestBuilder();
    }

    @Test
    public void shouldReturnAudioLinkWhenRequestingClient() throws ExternalServiceException {
        //given
        String expectedSynopsis = "This is a story of a guy that was coding a unit test";
        VoiceBunnyDto voiceBunnyDto = voiceBunnyDtoTestBuilder.build();
        configureStub(asJsonString(voiceBunnyDto), 200);

        //when
        String audioLink = voiceBunnyClient.bookSynopsis(expectedSynopsis);

        //then
        assertThat(audioLink, is("https://dropbox.com/sandbox/test.wav"));
    }

    private void configureStub(String response, int httpStatus) {
        stubFor(
                post(urlPathEqualTo("/projects/addBooking"))
                        .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON_VALUE))
                        .willReturn(aResponse()
                                .withStatus(httpStatus)
                                .withBody(response)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        )
        );
    }

}
