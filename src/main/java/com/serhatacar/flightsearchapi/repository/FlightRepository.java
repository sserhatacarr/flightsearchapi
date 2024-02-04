package com.serhatacar.flightsearchapi.repository;

import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
  List < Flight > findByArrivalAirport(Airport airport);
    List < Flight > findByDepartureAirport(Airport airport);
}
