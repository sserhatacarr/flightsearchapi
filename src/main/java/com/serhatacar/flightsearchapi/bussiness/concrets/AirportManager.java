package com.serhatacar.flightsearchapi.bussiness.concrets;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IAirportService;
import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;
import com.serhatacar.flightsearchapi.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportManager implements IAirportService {

    private final AirportRepository airportRepository;

    public AirportManager(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public List<Airport> getAll() {
        return null;
    }

    @Override
    public Airport getById(int id) {
        return null;
    }

    @Override
    public Airport add(Airport airport) {
        return null;
    }

    @Override
    public Airport update(Airport airport) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Airport> findByDepartureFlight(Flight flight) {
        return airportRepository.findAirportsByDepartureFlightsContains(flight);
    }

    @Override
    public List<Airport> findByArrivalFlight(Flight flight) {
        return airportRepository.findAirportsByArrivalFlightsContains(flight);
    }

}
