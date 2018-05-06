package com.booking.synopsis.controller;

import com.booking.synopsis.exceptions.ExternalServiceException;
import com.booking.synopsis.exceptions.InvalidRequestResourceException;
import com.booking.synopsis.request.BookingSynopsisRequestResource;
import com.booking.synopsis.service.BookingSynopsisService;
import com.booking.synopsis.validation.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.booking.synopsis.utils.BookingSynopsisUrls.BOOK_SYNOPSIS_ENDPOINT;


@RestController
public class BookingSynopsisController {

    private ValidationChecker validationChecker;
    private BookingSynopsisService bookingSynopsisService;

    @Autowired
    public BookingSynopsisController(ValidationChecker validationChecker,
                                     BookingSynopsisService bookingSynopsisService) {
        this.validationChecker = validationChecker;
        this.bookingSynopsisService = bookingSynopsisService;
    }

    @RequestMapping(value = BOOK_SYNOPSIS_ENDPOINT, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity bookSynopsis(@RequestBody BookingSynopsisRequestResource bookingSynopsisRequestResource) throws InvalidRequestResourceException, ExternalServiceException {
        validationChecker.validate(bookingSynopsisRequestResource);
        return new ResponseEntity(
                bookingSynopsisService.save(bookingSynopsisRequestResource.getMovieName()),
                HttpStatus.CREATED
        );
    }

}
