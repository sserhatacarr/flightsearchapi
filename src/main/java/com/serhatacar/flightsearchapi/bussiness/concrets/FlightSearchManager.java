package com.serhatacar.flightsearchapi.bussiness.concrets;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IFlightSearchService;
import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;
import com.serhatacar.flightsearchapi.repository.AirportRepository;
import com.serhatacar.flightsearchapi.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class FlightSearchManager implements IFlightSearchService {

    private FlightRepository flightRepository;
    private AirportRepository airportRepository;

    @Autowired
    public FlightSearchManager(FlightRepository flightRepository, AirportRepository airportRepository){
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public List<Flight> filterFlights(String departureCity, String arrivalCity, LocalDate departureDate) {

        LocalDateTime departureDateTimeBegin = departureDate.atStartOfDay();
        LocalDateTime departureDateTimeEnd = departureDate.atTime(23, 59, 59, 999999999);

        List<Airport> departureAirports = airportRepository.findByCity(departureCity);
        List<Airport> arrivalAirports = airportRepository.findByCity(arrivalCity);

        List<Flight> filteredFlights = flightRepository.findByDepartureAirportInAndArrivalAirportInAndDepartureDateTimeBetween(departureAirports, arrivalAirports, departureDateTimeBegin, departureDateTimeEnd);

        return filteredFlights;
    }

    @Override
    public List<Flight> filterFlights(String departureCity, String arrivalCity, OffsetDateTime departureDate, OffsetDateTime returnDate) {
        return null;
    }

    @Override
    public boolean isDateRangeValid(LocalDate departureDate, LocalDate returnDate) {
        return !departureDate.isAfter(returnDate);
    }

    @Override
    public boolean isCityValid(String city) {
        return !airportRepository.findByCity(city).isEmpty();
    }
}