package com.serhatacar.flightsearchapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {
    private Long id;
    private Long departureAirportId;
    private String departureAirportCity;
    private Long arrivalAirportId;
    private String arrivalAirportCity;
    private OffsetDateTime departureDateTime;
    private OffsetDateTime arrivalDateTime;
    private Double price;


}
