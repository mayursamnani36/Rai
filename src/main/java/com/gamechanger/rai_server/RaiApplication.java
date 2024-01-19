package com.gamechanger.rai_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RaiApplication.class, args);
	}

}
