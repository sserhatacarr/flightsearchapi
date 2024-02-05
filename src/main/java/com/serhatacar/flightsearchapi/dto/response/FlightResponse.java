package com.serhatacar.flightsearchapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightResponse {
    private Long id;
    private Long departureAirportId;
    private String departureAirportCity;
    private Long arrivalAirportId;
    private String arrivalAirportCity;
    private LocalDateTime departureDateTime;
    private LocalDateTime arrivalDateTime;
    private Double price;


}
