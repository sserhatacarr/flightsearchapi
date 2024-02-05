package com.serhatacar.flightsearchapi.dto.request.flight;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightRequest {
    private Long id;
    private Long departureAirportId;
    private Long arrivalAirportId;
    private OffsetDateTime departureDateTime;
    private OffsetDateTime arrivalDateTime;
    private Double price;


}
