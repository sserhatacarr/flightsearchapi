package com.serhatacar.flightsearchapi.bussiness.abstracts;

import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;

import java.util.List;

public interface IFlightService {
    List<Flight> getAll();
    Flight getById(int id);
    Flight add(Flight flight);
    Flight update(Flight flight);
    void delete(int id);
    List <Flight> findByDepartureAirportId(Airport airport);
    List <Flight> findByArrivalAirportId(Airport airport);
}
