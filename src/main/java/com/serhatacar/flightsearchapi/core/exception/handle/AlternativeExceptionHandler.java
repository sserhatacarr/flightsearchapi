package com.serhatacar.flightsearchapi.core.exception.handle;

import com.serhatacar.flightsearchapi.core.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AlternativeExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AlternativeErrorResponse> handleException(AirportNotFoundException exc) {
        AlternativeErrorResponse error = new AlternativeErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AlternativeErrorResponse> handleException(FlightNotFoundException exc) {
        AlternativeErrorResponse error = new AlternativeErrorResponse(HttpStatus.NOT_FOUND.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AlternativeErrorResponse> handleException(InvalidCityNameException exc) {
        AlternativeErrorResponse error = new AlternativeErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AlternativeErrorResponse> handleException(InvalidDateRangeException exc) {
        AlternativeErrorResponse error = new AlternativeErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AlternativeErrorResponse> handleException(InvalidPriceException exc) {
        AlternativeErrorResponse error = new AlternativeErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AlternativeErrorResponse> handleException(InvalidFlightDestinationException exc) {
        AlternativeErrorResponse error = new AlternativeErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AlternativeErrorResponse> handleException(UsernameInvalidException exc) {
        AlternativeErrorResponse error = new AlternativeErrorResponse(HttpStatus.BAD_REQUEST.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler //for all exception types
    public ResponseEntity<AlternativeErrorResponse> handleException(Exception exc) {
        AlternativeErrorResponse error = new AlternativeErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exc.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
