package org.projeto.gamelandia.simple;

import org.projeto.gamelandia.simple.utils.AppContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class AppMain {

	public static void main(String[] args) throws InterruptedException {
		
		// Starta a aplicação Spring Boot
		SpringApplication.run(AppContext.class, args);
	}

}
