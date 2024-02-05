package com.serhatacar.flightsearchapi.core.exception;

public class InvalidCityNameException  extends RuntimeException{
    public InvalidCityNameException(String message) {
        super(message);
    }
}
