package com.serhatacar.flightsearchapi.bussiness.abstracts;

import com.serhatacar.flightsearchapi.dto.request.airport.AirportDTO;
import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;

import java.util.List;

public interface IAirportService {
    List<Airport> getAll();
    Airport getById(Long id);
    Airport createAirport(Airport airport);
    Airport updateAirport(Airport airport);
    void deleteByID(Long id);
    boolean isAirportExist(Airport airport);
    boolean isCityValid(String city);
    AirportDTO mapAirportToAirportDTO(Airport airport);
    Airport mapAirportDTOToAirport(AirportDTO airportDTO);


}
