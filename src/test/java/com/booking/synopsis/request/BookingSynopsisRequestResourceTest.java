package com.booking.synopsis.request;

import com.booking.synopsis.exceptions.InvalidRequestResourceException;
import com.booking.synopsis.validation.ValidationChecker;
import com.booking.synopsis.validation.ValidationError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.validation.Validation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.booking.synopsis.validation.ValidationMessages.INVALID_MOVIE_NAME_MESSAGE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;

@RunWith(Parameterized.class)
public class BookingSynopsisRequestResourceTest {

    private ValidationChecker validationChecker;
    private String movieName;
    private List<String> errorMessages;

    public BookingSynopsisRequestResourceTest(String movieName, List<String> errorMessages) {
        this.movieName = movieName;
        this.errorMessages = errorMessages;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "", Arrays.asList(INVALID_MOVIE_NAME_MESSAGE)},
                { null, Arrays.asList(INVALID_MOVIE_NAME_MESSAGE)},
                { "Robocop 2", Arrays.asList()},
        });
    }

    @Before
    public void setUp() {
        validationChecker = new ValidationChecker(Validation.buildDefaultValidatorFactory().getValidator());
    }

    @Test
    public void shouldValidateBookingSynopsisRequestResource() {
        try {
            validationChecker.validate(new BookingSynopsisRequestResource(movieName));
            assertThat(errorMessages, empty());
        } catch (InvalidRequestResourceException ex) {
            assertThat(getErrorMessage(ex.getErrors()), is(errorMessages.toString()));
        }
    }

    private String getErrorMessage(List<ValidationError> errors) {
        return errors.stream()
                .map(ValidationError::getMessage)
                .collect(Collectors.toList())
                .toString();
    }
}
