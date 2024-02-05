package com.serhatacar.flightsearchapi;

import com.serhatacar.flightsearchapi.entity.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FlightsearchapiApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateFlight() {
        Flight flight = new Flight();
        flight.setDepartureDateTime(OffsetDateTime.now());
        flight.setArrivalDateTime(OffsetDateTime.now().plusDays(1));
        flight.setPrice(100.0);

        ResponseEntity<Flight> response = restTemplate.postForEntity("/flights", flight, Flight.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(flight.getPrice(), response.getBody().getPrice());
    }


}