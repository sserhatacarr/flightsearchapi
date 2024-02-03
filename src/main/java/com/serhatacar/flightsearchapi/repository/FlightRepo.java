package com.serhatacar.flightsearchapi.repository;

import com.serhatacar.flightsearchapi.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepo extends JpaRepository<Flight, Integer> {
}
