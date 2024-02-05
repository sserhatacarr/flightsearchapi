package com.serhatacar.flightsearchapi.dto.request.airport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirportDTO {
    private Long id;
    private String city;
}
