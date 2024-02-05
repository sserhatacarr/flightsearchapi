package com.serhatacar.flightsearchapi.core.exception;

public class InvalidFlightDestinationException extends RuntimeException{
    public InvalidFlightDestinationException(String message) {
        super(message);
    }
}
