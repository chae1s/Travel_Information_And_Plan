package com.example.Final_Project_9team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FinalProject9teamApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProject9teamApplication.class, args);
	}

}
