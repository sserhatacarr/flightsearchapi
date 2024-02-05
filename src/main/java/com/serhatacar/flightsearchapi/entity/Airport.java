package com.serhatacar.flightsearchapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "airports")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    @OneToMany(mappedBy = "departureAirport")
private List<Flight> departureFlights;
    @OneToMany(mappedBy = "arrivalAirport")
private List <Flight> arrivalFlights;


}
