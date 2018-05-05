package com.booking.synopsis.exceptions;

import com.booking.synopsis.response.SynopsisErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestResourceException.class)
    public ResponseEntity handleInvalidRequestResourceException(InvalidRequestResourceException ex) {
        return ResponseEntity.unprocessableEntity().body(new SynopsisErrorResponse(
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        ex.getMessage(),
                        ex.getErrors()
                )
        );
    }
}
