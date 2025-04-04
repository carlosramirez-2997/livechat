package com.carlosramirez.livechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class LivechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivechatApplication.class, args);
	}
}