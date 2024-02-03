package com.serhatacar.flightsearchapi.bussiness.concrets;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IFlightService;
import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;
import com.serhatacar.flightsearchapi.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightManager implements IFlightService {
    private final FlightRepository flightRepository;

    public FlightManager(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public List<Flight> getAll() {
        return null;
    }

    @Override
    public Flight getById(int id) {
        return null;
    }

    @Override
    public Flight add(Flight flight) {
        return null;
    }

    @Override
    public Flight update(Flight flight) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Flight> findByDepartureAirportId(Airport airport) {
        return flightRepository.findByDepartureAirport(airport);
    }

    @Override
    public List<Flight> findByArrivalAirportId(Airport airport) {
        return flightRepository.findByArrivalAirport(airport);
    }

}
