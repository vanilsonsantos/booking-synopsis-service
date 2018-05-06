package com.booking.synopsis.response;

import com.booking.synopsis.validation.ValidationError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SynopsisErrorResponse {
    private int code;
    private String message;
    private List<ValidationError> errors;
}
