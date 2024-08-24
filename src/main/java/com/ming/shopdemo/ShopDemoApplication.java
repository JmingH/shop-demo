package com.ming.shopdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@EnableJpaAuditing
@SpringBootApplication
public class ShopDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopDemoApplication.class, args);
	}

}
