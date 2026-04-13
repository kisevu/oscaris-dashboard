package com.oscaris.caterers.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"com.oscaris.caterers.auth"})
@EnableJpaRepositories(basePackages = "com.oscaris.caterers.auth.repos")
@EntityScan(basePackages = "com.oscaris.caterers.auth.entities")
public class AuthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
