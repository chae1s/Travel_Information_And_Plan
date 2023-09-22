package com.example.eodi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TravelInformationAndPlanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelInformationAndPlanApplication.class, args);
	}

}
