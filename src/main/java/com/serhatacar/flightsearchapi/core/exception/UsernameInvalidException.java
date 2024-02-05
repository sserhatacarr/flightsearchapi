package com.serhatacar.flightsearchapi.core.exception;

public class UsernameInvalidException extends RuntimeException{
    public UsernameInvalidException(String message) {
        super(message);
    }
}
