package com.serhatacar.flightsearchapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightSearchResponse {
    private List<FlightResponse> departureFlights;
    private List<FlightResponse> returnFlights;
}
