package com.serhatacar.flightsearchapi.bussiness.abstracts;

import com.serhatacar.flightsearchapi.entity.Flight;

import java.util.List;

public interface IScheduledJobService {
    void fetchFlightsDaily();
    List<Flight> mockAPICall();
}
