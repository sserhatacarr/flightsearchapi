package com.serhatacar.flightsearchapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class FlightsearchapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightsearchapiApplication.class, args);
	}

}
