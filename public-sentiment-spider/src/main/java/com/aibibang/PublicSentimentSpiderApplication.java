package com.aibibang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ImportResource(locations = "classpath:application-context.xml")
public class PublicSentimentSpiderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicSentimentSpiderApplication.class, args);
	}
}
