package com.serhatacar.flightsearchapi.bussiness.concrets;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IScheduledJobService;
import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;
import com.serhatacar.flightsearchapi.repository.AirportRepository;
import com.serhatacar.flightsearchapi.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ScheduledJobManager implements IScheduledJobService {
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public ScheduledJobManager(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
    }

    @Override
    @Scheduled(cron = "0 0 0 * * *") // every day at 00:00
    public void fetchFlightsDaily() {
        System.out.println("fetchFlightsDaily() is called");
        List<Flight> fetchedFlightsFromThirdPartyAPI = mockAPICall();
        flightRepository.saveAllAndFlush(fetchedFlightsFromThirdPartyAPI);
        System.out.println("Fetched flights are saved to the database");

    }

    @Override
    public List<Flight> mockAPICall() {
        Random random = new Random();

        List<Flight> generatedFlightData = new ArrayList<>();

        List<Airport> airports = airportRepository.findAll();
        int len = airports.size();

        LocalDateTime dateTime1 = LocalDateTime.now().plusDays(7).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime dateTime2 = dateTime1.plusHours(1).plusMinutes(20);

        for (int i = 0; i < 2; i++) {

            BigDecimal price = new BigDecimal(random.nextInt(50, 500) * 10);
            BigDecimal price2 = new BigDecimal(random.nextInt(50, 500) * 10);
            BigDecimal price3 = new BigDecimal(random.nextInt(50, 500) * 10);
            BigDecimal price4 = new BigDecimal(random.nextInt(50, 500) * 10);
            BigDecimal price5 = new BigDecimal(random.nextInt(50, 500) * 10);

            Flight flight1 = new Flight(0L, airports.get(0), airports.get(1), dateTime1, dateTime2, price.doubleValue());
            Flight flight2 = new Flight(0L, airports.get(1), airports.get(0), dateTime1.plusMinutes(30), dateTime2.plusMinutes(30), price2.doubleValue());
            Flight flight3 = new Flight(0L, airports.get(2), airports.get(3), dateTime1.plusMinutes(60), dateTime2.plusMinutes(60), price3.doubleValue());
            Flight flight4 = new Flight(0L, airports.get(3), airports.get(2), dateTime1.plusMinutes(90), dateTime2.plusMinutes(90), price4.doubleValue());
            Flight flight5 = new Flight(0L, airports.get(4), airports.get(5), dateTime1.plusMinutes(120), dateTime2.plusMinutes(120), price5.doubleValue());

            generatedFlightData.add(flight1);
            generatedFlightData.add(flight2);
            generatedFlightData.add(flight3);
            generatedFlightData.add(flight4);
            generatedFlightData.add(flight5);

            dateTime1 = dateTime1.plusHours(4);
            dateTime2 = dateTime1.plusHours(1).plusMinutes(20);
        }

        return generatedFlightData;
    }
}
