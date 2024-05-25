package com.example.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableWebSecurity
public class Laborator11Application {

	public static void main(String[] args) {
		SpringApplication.run(Laborator11Application.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(ApiService apiService) {
		return args -> {
			System.out.println("Getting all authors...");
			apiService.getAllAuthors().subscribe(author -> System.out.println(author.getName()));
		};
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeRequests().anyRequest().permitAll();
		return http.build();
	}
}