package com.serhatacar.flightsearchapi.api;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IFlightService;
import com.serhatacar.flightsearchapi.bussiness.concrets.AirportManager;
import com.serhatacar.flightsearchapi.core.exception.AirportNotFoundException;
import com.serhatacar.flightsearchapi.core.exception.FlightNotFoundException;
import com.serhatacar.flightsearchapi.core.exception.InvalidDateRangeException;
import com.serhatacar.flightsearchapi.core.exception.InvalidFlightDestinationException;
import com.serhatacar.flightsearchapi.dto.request.flight.FlightRequest;
import com.serhatacar.flightsearchapi.dto.response.FlightResponse;
import com.serhatacar.flightsearchapi.entity.Flight;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")
@SecurityRequirement(name="bearerAuth")

public class FlightController {
    private final IFlightService flightService;
    private final AirportManager airportManager;

    @Autowired
    public FlightController(IFlightService flightService, AirportManager airportManager) {
        this.flightService = flightService;
        this.airportManager = airportManager;
    }
    @Operation
    @GetMapping
    public ResponseEntity<List<FlightResponse>> getAllFlights() {
        List<Flight> flights = flightService.getAll();

        List<FlightResponse> flightResponses = flights.stream()
                .map(flightService::mapFlightToFlightResponse)
                .toList();

        return new ResponseEntity<>(flightResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) {
    Flight flight = flightService.getById(id);
    if (flight == null) {
        throw new FlightNotFoundException("No flight found with the provided id: " + id);
    }
    FlightResponse flightResponse = flightService.mapFlightToFlightResponse(flight);
    return new ResponseEntity<>(flightResponse, HttpStatus.OK);
}

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(@RequestBody FlightRequest flightRequest) {
        flightRequest.setId(null);

        if (flightRequest.getDepartureAirportId() == null || !flightService.isAirportExist(airportManager.getById(flightRequest.getDepartureAirportId()))) {
            throw new AirportNotFoundException("No departure airport found with the provided id: " + flightRequest.getDepartureAirportId());
        }

        if (flightRequest.getArrivalAirportId() == null || !flightService.isAirportExist(airportManager.getById(flightRequest.getArrivalAirportId()))) {
            throw new InvalidFlightDestinationException("No arrival airport found with the provided id: " + flightRequest.getArrivalAirportId());
        }

        if (flightRequest.getDepartureDateTime().isAfter(flightRequest.getArrivalDateTime()) || flightRequest.getDepartureDateTime().isEqual(flightRequest.getArrivalDateTime())) {
            throw new InvalidDateRangeException("Unable to add flight. Departure date and time cannot be later than or equal to arrival date and time.");
        }

        if (flightRequest.getArrivalAirportId().equals(flightRequest.getDepartureAirportId())) {
            throw new InvalidFlightDestinationException("Unable to add flight. Departure and arrival airports cannot be identical.");
        }

        Flight flight = flightService.mapFlightRequestToFlight(flightRequest);

        Flight createdFlight = flightService.createFlight(flight);

        FlightResponse flightResponse = flightService.mapFlightToFlightResponse(createdFlight);

        return new ResponseEntity<>(flightResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long id, @RequestBody FlightRequest flightRequest) {
        if (flightRequest.getId() == null || !flightService.isFlightExist(flightService.getById(flightRequest.getId()))) {
            throw new RuntimeException("No flight found with the provided id: " + flightRequest.getId());
        }

        if (flightRequest.getDepartureAirportId() == null || !flightService.isAirportExist(airportManager.getById(flightRequest.getDepartureAirportId()))) {
            throw new RuntimeException("No departure airport found with the provided id: " + flightRequest.getDepartureAirportId());
        }

        if (flightRequest.getArrivalAirportId() == null || !flightService.isAirportExist(airportManager.getById(flightRequest.getArrivalAirportId()))) {
            throw new RuntimeException("No arrival airport found with the provided id: " + flightRequest.getArrivalAirportId());
        }

        Flight flight = flightService.mapFlightRequestToFlight(flightRequest);

        Flight updatedFlight = flightService.updateFlight(flight);

        FlightResponse flightResponse = flightService.mapFlightToFlightResponse(updatedFlight);

        return new ResponseEntity<>(flightResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        if (!flightService.isFlightExist(flightService.getById(id))) {
            throw new RuntimeException("No flight found with the provided id: " + id);
        }
        flightService.deleteByID(id);
        return new ResponseEntity<>("Flight with id " + id + " has been deleted.", HttpStatus.OK);
    }
}