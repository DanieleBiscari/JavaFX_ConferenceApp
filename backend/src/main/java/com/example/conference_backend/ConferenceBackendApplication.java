package com.example.conference_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ConferenceBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConferenceBackendApplication.class, args);
	}

}
