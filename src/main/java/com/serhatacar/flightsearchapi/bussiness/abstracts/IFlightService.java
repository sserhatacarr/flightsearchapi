package com.serhatacar.flightsearchapi.bussiness.abstracts;

import com.serhatacar.flightsearchapi.dto.request.flight.FlightRequest;
import com.serhatacar.flightsearchapi.dto.response.FlightResponse;
import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface IFlightService {
    List<Flight> getAll();

    Flight getById(Long id);

    Flight createFlight(Flight flight);

    Flight updateFlight(Flight flight);

    void deleteByID(Long id);

    boolean isFlightExist(Flight flight);

    boolean isAirportExist(Airport airport);

    boolean isPriceAppropriate(Double price);

    boolean isDateAppropriate(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime);

    Flight mapFlightRequestToFlight(FlightRequest flightRequest);

    FlightResponse mapFlightToFlightResponse(Flight flight);
}
