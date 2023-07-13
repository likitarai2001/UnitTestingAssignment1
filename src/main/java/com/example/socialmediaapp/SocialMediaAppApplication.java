package com.example.socialmediaapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
@Slf4j
public class SocialMediaAppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SocialMediaAppApplication.class, args);
		Environment env = context.getEnvironment();
		String port = env.getProperty("server.port");
		log.info("Server started on port " + port);
	}
}
