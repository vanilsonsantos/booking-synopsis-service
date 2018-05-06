package com.booking.synopsis.controller;

import com.booking.synopsis.BookingSynopsisApplication;
import com.booking.synopsis.exceptions.ExternalServiceException;
import com.booking.synopsis.exceptions.GlobalExceptionHandler;
import com.booking.synopsis.exceptions.InvalidRequestResourceException;
import com.booking.synopsis.request.BookingSynopsisRequestResource;
import com.booking.synopsis.request.BookingSynopsisRequestResourceTestBuilder;
import com.booking.synopsis.response.Synopsis;
import com.booking.synopsis.response.SynopsisErrorResponse;
import com.booking.synopsis.response.SynopsisErrorResponseTestBuilder;
import com.booking.synopsis.response.SynopsisTestBuilder;
import com.booking.synopsis.service.BookingSynopsisService;
import com.booking.synopsis.validation.ValidationChecker;
import com.booking.synopsis.validation.ValidationError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static com.booking.synopsis.utils.BookingSynopsisUrls.BOOK_SYNOPSIS_ENDPOINT;
import static com.booking.synopsis.utils.JsonUtils.asJsonString;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static net.javacrumbs.jsonunit.core.Option.IGNORING_VALUES;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BookingSynopsisApplication.class)
@SpringBootTest()
public class BookingSynopsisControllerTest {

    @Mock
    private ValidationChecker validationChecker;

    @Mock
    private BookingSynopsisService bookingSynopsisService;

    @InjectMocks
    private BookingSynopsisController bookingSynopsisController;

    private MockMvc mvc;
    private SynopsisErrorResponseTestBuilder synopsisErrorResponseTestBuilder;
    private BookingSynopsisRequestResourceTestBuilder bookingSynopsisRequestResourceTestBuilder;
    private SynopsisTestBuilder synopsisTestBuilder;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(bookingSynopsisController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        synopsisErrorResponseTestBuilder = new SynopsisErrorResponseTestBuilder();
        bookingSynopsisRequestResourceTestBuilder = new BookingSynopsisRequestResourceTestBuilder();
        synopsisTestBuilder = new SynopsisTestBuilder();
    }

    @Test
    public void shouldReturn422StatusIfRequestBodyIsInvalidWhenBookingNewSynopsis() throws Exception {
        //given
        BookingSynopsisRequestResource bookingSynopsisRequestResource = bookingSynopsisRequestResourceTestBuilder
                .withMovieName(null)
                .build();
        doThrow(new InvalidRequestResourceException(
                Collections.singletonList(new ValidationError("movieName", "Invalid movie name")),
                "Invalid movie name"
        )).when(validationChecker).validate(any());

        //when
        ResultActions response = mvc.perform(post(BOOK_SYNOPSIS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookingSynopsisRequestResource))
        );

        //then
        response.andExpect(status().is(422));
        assertJsonEquals(
                asJsonString(synopsisErrorResponseTestBuilder.build()),
                response.andReturn().getResponse().getContentAsString(),
                net.javacrumbs.jsonunit.JsonAssert.when(IGNORING_VALUES)
        );
    }

    @Test
    public void shouldReturn424StatusIfAnyExternalServiceThrowAnException() throws Exception {
        //given
        BookingSynopsisRequestResource bookingSynopsisRequestResource = bookingSynopsisRequestResourceTestBuilder.build();
        doThrow(new ExternalServiceException("Movie not found")).when(bookingSynopsisService).save(any());

        //when
        ResultActions response = mvc.perform(post(BOOK_SYNOPSIS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookingSynopsisRequestResource))
        );

        //then
        response.andExpect(status().is(424));
        SynopsisErrorResponse synopsisErrorResponse = synopsisErrorResponseTestBuilder
                .withErrors(null)
                .build();
        assertJsonEquals(
                asJsonString(synopsisErrorResponse),
                response.andReturn().getResponse().getContentAsString(),
                net.javacrumbs.jsonunit.JsonAssert.when(IGNORING_VALUES)
        );
    }

    @Test
    public void shouldReturn201StatusWhenBookingNewSynopsis() throws Exception {
        //given
        BookingSynopsisRequestResource bookingSynopsisRequestResource = bookingSynopsisRequestResourceTestBuilder.build();
        Synopsis expectedSynopsisResponse = synopsisTestBuilder.build();
        when(bookingSynopsisService.save(any())).thenReturn(expectedSynopsisResponse);

        //when
        ResultActions response = mvc.perform(post(BOOK_SYNOPSIS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookingSynopsisRequestResource))
        );

        //then
        verify(bookingSynopsisService).save(bookingSynopsisRequestResource.getMovieName());
        response.andExpect(status().is(201));
        assertJsonEquals(
                asJsonString(expectedSynopsisResponse),
                response.andReturn().getResponse().getContentAsString(),
                net.javacrumbs.jsonunit.JsonAssert.when(IGNORING_VALUES)
        );
    }
}
