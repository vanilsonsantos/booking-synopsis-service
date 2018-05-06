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

    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity handleExternalServiceException(ExternalServiceException ex) {
        SynopsisErrorResponse synopsisErrorResponse = new SynopsisErrorResponse(
                HttpStatus.FAILED_DEPENDENCY.value(),
                ex.getCause().getMessage(),
                null
        );
        return new ResponseEntity(synopsisErrorResponse, HttpStatus.FAILED_DEPENDENCY);
    }
}
