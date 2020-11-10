package com.example.dependent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DependentApplication {

	public static void main(String[] args) {
		SpringApplication.run(DependentApplication.class, args);
	}

}
