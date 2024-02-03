package com.serhatacar.flightsearchapi.bussiness.abstracts;

import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;

import java.util.List;

public interface IAirportService {
    List<Airport> getAll();
    Airport getById(int id);
    Airport add(Airport airport);
    Airport update(Airport airport);
    void delete(int id);
    List < Airport > findByDepartureFlight(Flight flight);
    List < Airport > findByArrivalFlight(Flight flight);

}
