package com.serhatacar.flightsearchapi.repository;

import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirportInAndArrivalAirportInAndDepartureDateTimeBetween(List<Airport> departureAirports, List<Airport> arrivalAirports, LocalDateTime departureDateTimeBegin, LocalDateTime departureDatetimeEnd);

}
