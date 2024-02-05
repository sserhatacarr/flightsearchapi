package com.serhatacar.flightsearchapi.bussiness.abstracts;

import com.serhatacar.flightsearchapi.entity.Flight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public interface IFlightSearchService {
    List<Flight> filterFlights(String departureCity, String arrivalCity, LocalDate departureDate);
    List<Flight> filterFlights(String departureCity, String arrivalCity, LocalDate departureDate, LocalDate returnDate);
    boolean isDateRangeValid(LocalDate departureDate, LocalDate returnDate);
    boolean isCityValid(String city);
}
