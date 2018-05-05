package com.booking.synopsis.validation;

import com.booking.synopsis.exceptions.InvalidRequestResourceException;
import com.booking.synopsis.request.RequestResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationChecker {

    private Validator validator;
    private static final String ERROR_MESSAGE = "Validation Failed";

    @Autowired
    public ValidationChecker(Validator validator) {
        this.validator = validator;
    }

    public void validate(RequestResource requestParameterResource) throws InvalidRequestResourceException {

        Set<ConstraintViolation<RequestResource>> constraintViolations = validator.validate(requestParameterResource);

        if(!constraintViolations.isEmpty()) {

            List<ValidationError> errors = constraintViolations.stream()
                    .map(error -> new ValidationError(error.getPropertyPath().toString(), error.getMessage()))
                    .collect(Collectors.toList());

            throw new InvalidRequestResourceException(errors, ERROR_MESSAGE);
        }
    }
}
