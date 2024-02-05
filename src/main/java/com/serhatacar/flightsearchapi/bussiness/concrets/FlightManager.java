package com.serhatacar.flightsearchapi.bussiness.concrets;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IFlightService;
import com.serhatacar.flightsearchapi.dto.request.flight.FlightRequest;
import com.serhatacar.flightsearchapi.dto.response.FlightResponse;
import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;
import com.serhatacar.flightsearchapi.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FlightManager implements IFlightService {
    private final FlightRepository flightRepository;
    private final AirportManager airportManager;

    @Autowired
    public FlightManager(FlightRepository flightRepository, AirportManager airportManager) {
        this.flightRepository = flightRepository;
        this.airportManager = airportManager;
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public Flight getById(Long id) {
        Optional<Flight> result = flightRepository.findById(id);
        Flight flight = null;
        if (result.isPresent()) {
            flight = result.get();
        } else {
            throw new RuntimeException("Did not find flight id - " + id);
        }
        return flight;
    }

    @Override
    public Flight createFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    @Override
    public Flight updateFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public boolean isAirportExist(Airport airport) {
        return airportManager.isAirportExist(airport);
    }

    @Override
    public void deleteByID(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    public boolean isFlightExist(Flight flight) {
        return flightRepository.existsById(flight.getId());
    }


    @Override
    public boolean isPriceAppropriate(Double price) {
        return price > 0;
    }

    @Override
    public boolean isDateAppropriate(LocalDateTime departureDateTime, LocalDateTime arrivalDateTime) {
        return departureDateTime.isBefore(arrivalDateTime);
    }

    @Override
    public Flight mapFlightRequestToFlight(FlightRequest flightRequest) {
        Flight flight = new Flight();
        if (flightRequest.getId() != null && flightRequest.getId() > 0) {
            flight = getById(flightRequest.getId());
        }
        if (flightRequest.getDepartureDateTime() != null) {
            flight.setDepartureDateTime(flightRequest.getDepartureDateTime());
        }
        if (flightRequest.getArrivalDateTime() != null) {
            flight.setArrivalDateTime(flightRequest.getArrivalDateTime());
        }
        if (flightRequest.getPrice() != null) {
            flight.setPrice(flightRequest.getPrice());
        }
        if (flightRequest.getDepartureAirportId() != null) {
            flight.setDepartureAirport(airportManager.getById(flightRequest.getDepartureAirportId()));
        }
        if (flightRequest.getArrivalAirportId() != null) {
            flight.setArrivalAirport(airportManager.getById(flightRequest.getArrivalAirportId()));
        }
        return flight;
    }

    @Override
    public FlightResponse mapFlightToFlightResponse(Flight flight) {
        FlightResponse flightResponse = new FlightResponse();

        flightResponse.setId(flight.getId());

        if (flight.getDepartureDateTime() != null) {
            flightResponse.setDepartureDateTime(flight.getDepartureDateTime());
        }
        if (flight.getArrivalDateTime() != null) {
            flightResponse.setArrivalDateTime(flight.getArrivalDateTime());
        }
        if (flight.getPrice() != null) {
            flightResponse.setPrice(flight.getPrice());
        }
        if (flight.getDepartureAirport() != null) {
            flightResponse.setArrivalAirportCity(flight.getArrivalAirport().getCity());
            flightResponse.setArrivalAirportId(flight.getArrivalAirport().getId());
        }
        if (flight.getArrivalAirport() != null) {
            flightResponse.setDepartureAirportCity(flight.getDepartureAirport().getCity());
            flightResponse.setDepartureAirportId(flight.getDepartureAirport().getId());
        }
        return flightResponse;

    }


}
