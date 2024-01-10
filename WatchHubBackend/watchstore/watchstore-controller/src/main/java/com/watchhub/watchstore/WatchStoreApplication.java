package com.watchhub.watchstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

/**
 * The main class for the Watch Store application.
 * This class contains the entry point for running the application.
 */
@SpringBootApplication
@ComponentScan("com.watchhub.watchstore")
@EnableJpaAuditing
@OpenAPIDefinition(info=@Info(
		title="Watch Store API",
		description="Watch Store api details",
		version="1.0",
		contact=@Contact(
				email="imsharif098@gmail.com",
				name="Md Sharif"
				),
		license=@License(
				name="Sharif",
				url="https://www.sharif.com"
				)
		))
public class WatchStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WatchStoreApplication.class, args);
	}
}
