package com.serhatacar.flightsearchapi.api;

import com.serhatacar.flightsearchapi.bussiness.abstracts.IAirportService;
import com.serhatacar.flightsearchapi.core.exception.AirportNotFoundException;
import com.serhatacar.flightsearchapi.dto.request.airport.AirportDTO;
import com.serhatacar.flightsearchapi.entity.Airport;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/airports")
@SecurityRequirement(name="bearerAuth")
public class AirportController {
    private final IAirportService airportService;

    @Autowired
    public AirportController(IAirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AirportDTO>> getAllAirports() {
        List<Airport> airports = airportService.getAll();

        List<AirportDTO> airportDTOS = airports.stream()
                .map(airportService::mapAirportToAirportDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(airportDTOS, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AirportDTO> getAirportById(@PathVariable Long id) {
        Airport airport = airportService.getById(id);
        if (airport == null) {
            throw new AirportNotFoundException("Airport with id " + id + " not found.");
        }
        AirportDTO airportDTO = airportService.mapAirportToAirportDTO(airport);
        return new ResponseEntity<>(airportDTO, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AirportDTO> createAirport(@RequestBody AirportDTO airportDTO) {
        airportDTO.setId(null);
        if (!airportService.isCityValid(airportDTO.getCity())) {
            throw new AirportNotFoundException("City is not valid.");
        }
        Airport airport = airportService.mapAirportDTOToAirport(airportDTO);
        Airport createdAirport = airportService.createAirport(airport);
        AirportDTO createdAirportDTO = airportService.mapAirportToAirportDTO(createdAirport);
        return new ResponseEntity<>(createdAirportDTO, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AirportDTO> updateAirport(@PathVariable Long id, @RequestBody AirportDTO airportDTO) {
        if (!airportService.isAirportExist(airportService.getById(id))) {
            throw new AirportNotFoundException("Airport with id " + id + " not found.");
        }
        if (!airportService.isCityValid(airportDTO.getCity())) {
            throw new AirportNotFoundException("City is not valid.");
        }
        airportDTO.setId(id);
        Airport airport = airportService.mapAirportDTOToAirport(airportDTO);
        Airport updatedAirport = airportService.updateAirport(airport);
        AirportDTO updatedAirportDTO = airportService.mapAirportToAirportDTO(updatedAirport);
        return new ResponseEntity<>(updatedAirportDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> deleteAirport(@PathVariable Long id) {
        if (!airportService.isAirportExist(airportService.getById(id))) {
            throw new AirportNotFoundException("Airport with id " + id + " not found.");
        }
        airportService.deleteByID(id);
        return new ResponseEntity<>("Airport with id " + id + " deleted.", HttpStatus.OK);
    }


}
