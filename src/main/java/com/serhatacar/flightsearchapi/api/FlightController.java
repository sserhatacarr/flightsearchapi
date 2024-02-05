package com.serhatacar.flightsearchapi.api;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IFlightService;
import com.serhatacar.flightsearchapi.bussiness.concrets.AirportManager;
import com.serhatacar.flightsearchapi.dto.request.flight.FlightRequest;
import com.serhatacar.flightsearchapi.dto.response.FlightResponse;
import com.serhatacar.flightsearchapi.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flights")

public class FlightController {
    private final IFlightService flightService;
    private final AirportManager airportManager;

    @Autowired
    public FlightController(IFlightService flightService, AirportManager airportManager) {
        this.flightService = flightService;
        this.airportManager = airportManager;
    }

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
            throw new IllegalArgumentException("Flight with id " + id + " not found.");
        }
        FlightResponse flightResponse = flightService.mapFlightToFlightResponse(flight);
        return new ResponseEntity<>(flightResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FlightResponse> createFlight(@RequestBody FlightRequest flightRequest) {
        if (flightRequest.getId() != null) {
            throw new RuntimeException("Providing id during add flight not allowed.");
        }

        if (flightRequest.getDepartureAirportId() == null || !flightService.isAirportExist(airportManager.getById(flightRequest.getDepartureAirportId()))) {
            throw new RuntimeException("Departure airport with given id: " + flightRequest.getDepartureAirportId() + " does not exist.");
        }

        if (flightRequest.getArrivalAirportId() == null || !flightService.isAirportExist(airportManager.getById(flightRequest.getArrivalAirportId()))) {
            throw new RuntimeException("Arrival airport with given id: " + flightRequest.getArrivalAirportId() + " does not exist.");
        }

        Flight flight = flightService.mapFlightRequestToFlight(flightRequest);

        Flight createdFlight = flightService.createFlight(flight);

        FlightResponse flightResponse = flightService.mapFlightToFlightResponse(createdFlight);

        return new ResponseEntity<>(flightResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FlightResponse> updateFlight(@PathVariable Long id, @RequestBody FlightRequest flightRequest) {
        if (flightRequest.getId() == null || !flightService.isFlightExist(flightService.getById(flightRequest.getId()))) {
            throw new RuntimeException("Flight with given id: " + flightRequest.getId() + " does not exist.");
        }

        if (flightRequest.getDepartureAirportId() == null || !flightService.isAirportExist(airportManager.getById(flightRequest.getDepartureAirportId()))) {
            throw new RuntimeException("Departure airport with given id: " + flightRequest.getDepartureAirportId() + " does not exist.");
        }

        if (flightRequest.getArrivalAirportId() == null || !flightService.isAirportExist(airportManager.getById(flightRequest.getArrivalAirportId()))) {
            throw new RuntimeException("Arrival airport with given id: " + flightRequest.getArrivalAirportId() + " does not exist.");
        }

        Flight flight = flightService.mapFlightRequestToFlight(flightRequest);

        Flight updatedFlight = flightService.updateFlight(flight);

        FlightResponse flightResponse = flightService.mapFlightToFlightResponse(updatedFlight);

        return new ResponseEntity<>(flightResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        if (!flightService.isFlightExist(flightService.getById(id))) {
            throw new RuntimeException("Flight with given id: " + id + " does not exist.");
        }
        flightService.deleteByID(id);
        return new ResponseEntity<>("Flight with id " + id + " deleted.", HttpStatus.OK);
    }
}