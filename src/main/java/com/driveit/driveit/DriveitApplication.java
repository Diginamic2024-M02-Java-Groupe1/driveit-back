package com.driveit.driveit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DriveitApplication {



	public static void main(String[] args) {
		SpringApplication.run(DriveitApplication.class, args);
		System.out.println("l'application est lancée.");
	}
}
