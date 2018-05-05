package com.booking.synopsis.controller;

import com.booking.synopsis.exceptions.InvalidRequestResourceException;
import com.booking.synopsis.request.BookingSynopsisRequestResource;
import com.booking.synopsis.validation.ValidationChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.booking.synopsis.utils.BookingSynopsisUrls.BOOK_SYNOPSIS_ENDPOINT;


@RestController
public class BookingSynopsisController {

    private ValidationChecker validationChecker;

    @Autowired
    public BookingSynopsisController(ValidationChecker validationChecker) {
        this.validationChecker = validationChecker;
    }

    @RequestMapping(value = BOOK_SYNOPSIS_ENDPOINT, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity bookSynopsis(@RequestBody BookingSynopsisRequestResource bookingSynopsisRequestResource) throws InvalidRequestResourceException {
        validationChecker.validate(bookingSynopsisRequestResource);
        return ResponseEntity.ok().build();
    }

}
