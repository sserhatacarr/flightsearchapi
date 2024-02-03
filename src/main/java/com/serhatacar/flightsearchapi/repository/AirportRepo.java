package com.serhatacar.flightsearchapi.repository;

import com.serhatacar.flightsearchapi.entity.Airport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirportRepo extends JpaRepository<Airport, Integer> {
}
