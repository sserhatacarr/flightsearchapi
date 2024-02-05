package com.serhatacar.flightsearchapi.api;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IFlightSearchService;
import com.serhatacar.flightsearchapi.bussiness.abstracts.IFlightService;
import com.serhatacar.flightsearchapi.dto.response.FlightSearchResponse;
import com.serhatacar.flightsearchapi.dto.response.FlightResponse;
import com.serhatacar.flightsearchapi.core.exception.AirportNotFoundException;
import com.serhatacar.flightsearchapi.core.exception.InvalidDateRangeException;
import com.serhatacar.flightsearchapi.core.exception.InvalidFlightDestinationException;
import com.serhatacar.flightsearchapi.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/flight")
public class FlightSearchController {
    private final IFlightSearchService flightSearchService;
    private final IFlightService flightService;

    @Autowired
    public FlightSearchController(IFlightSearchService flightSearchService, IFlightService flightService) {
        this.flightSearchService = flightSearchService;
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<FlightSearchResponse> getFlightWithFilters(
            @RequestParam(name = "departureCity") String departureCity,
            @RequestParam(name = "arrivalCity") String arrivalCity,
            @RequestParam(name = "departureDate") LocalDate departureDate,
            @RequestParam(name = "returnDate", required = false) LocalDate returnDate) {

        if (!flightSearchService.isCityValid(departureCity)) {
            throw new AirportNotFoundException("Cannot get flights. No airport city exists with name: " + departureCity);
        }

        if (!flightSearchService.isCityValid(arrivalCity)) {
            throw new AirportNotFoundException("Cannot get flights. No airport city exists with name: " + arrivalCity);
        }

        if (departureCity.equals(arrivalCity)) {
            throw new InvalidFlightDestinationException("Cannot get flights. Departure and arrival cities cannot be the same.");
        }

        List<Flight> departureFlights = flightSearchService.filterFlights(departureCity, arrivalCity, departureDate);

        List<FlightResponse> departureFlightResponses = departureFlights.stream()
                .map(flightService::mapFlightToFlightResponse)
                .collect(Collectors.toList());

        FlightSearchResponse flightSearchResponse = new FlightSearchResponse();

        flightSearchResponse.setDepartureFlights(departureFlightResponses);
        flightSearchResponse.setReturnFlights(null);

        if (returnDate != null) {

            if (!flightSearchService.isDateRangeValid(departureDate, returnDate)) {
                throw new InvalidDateRangeException("Cannot get flights. Departure date cannot be bigger than return date.");
            }

            //for return flights, departure and arrival city information will be swapped between each other
            List<Flight> returnFlights = flightSearchService.filterFlights(arrivalCity, departureCity, returnDate);

            List<FlightResponse> returnFlightResponses = returnFlights.stream()
                    .map(flightService::mapFlightToFlightResponse)
                    .collect(Collectors.toList());

            flightSearchResponse.setReturnFlights(returnFlightResponses);
        }

        return new ResponseEntity<>(flightSearchResponse, HttpStatus.OK);
    }
}