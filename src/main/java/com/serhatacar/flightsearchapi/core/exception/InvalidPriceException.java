package com.serhatacar.flightsearchapi.core.exception;

public class InvalidPriceException extends RuntimeException{
    public InvalidPriceException(String message) {
        super(message);
    }
}