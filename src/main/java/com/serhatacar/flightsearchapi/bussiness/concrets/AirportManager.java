package com.serhatacar.flightsearchapi.bussiness.concrets;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IAirportService;
import com.serhatacar.flightsearchapi.dto.request.airport.AirportDTO;
import com.serhatacar.flightsearchapi.entity.Airport;
import com.serhatacar.flightsearchapi.entity.Flight;
import com.serhatacar.flightsearchapi.repository.AirportRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportManager implements IAirportService {

    private final AirportRepository airportRepository;

    public AirportManager(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public List<Airport> getAll() {
        return airportRepository.findAll();
    }

    @Override
    public Airport getById(Long id) {
        return airportRepository.findById(id).orElse(null);
    }

    @Override
    public Airport createAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public Airport updateAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public void deleteByID(Long id) {
        airportRepository.deleteById(id);

    }


    @Override
    public AirportDTO mapAirportToAirportDTO(Airport airport) {
        AirportDTO airportDTO = new AirportDTO();
        if (airport.getCity() != null) {
            airportDTO.setCity(airport.getCity());
        }
        airportDTO.setId(airport.getId());
        return airportDTO;
    }

    @Override
    public Airport mapAirportDTOToAirport(AirportDTO airportDTO) {
        Airport airport = new Airport();
        if (airportDTO.getId() != null && airportDTO.getId() > 0) {
            airport = getById(airportDTO.getId());
        }
        if (airportDTO.getCity() != null) {
            airport.setCity(airportDTO.getCity());
        }
        return airport;
    }

    @Override
    public boolean isAirportExist(Airport airport) {
        return airport != null && airport.getId() != null && airportRepository.existsById(airport.getId());
    }

    @Override
    public boolean isCityValid(String city) {
        return city != null && !city.trim().isEmpty();
    }
}
