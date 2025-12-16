package com.udacity.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@org.springframework.boot.autoconfigure.SpringBootApplication
public class EcommerceApplication {
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
