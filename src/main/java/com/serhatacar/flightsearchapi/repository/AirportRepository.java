package com.serhatacar.flightsearchapi.repository;

import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AirportRepository extends JpaRepository<Airport, Long> {
    List<Airport> findAirportsByArrivalFlightsContains(Flight flight);
    List<Airport> findAirportsByDepartureFlightsContains(Flight flight);
}
