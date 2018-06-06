package com.pz.broadcast;

import com.pz.broadcast.services.file.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ImportResource("classpath:broadcast-database-data.xml")
@ComponentScan({
        "com.pz.broadcast.config",
        "com.pz.broadcast.controllers",
        "com.pz.broadcast.interceptors",
        "com.pz.broadcast.services",
        "com.pz.broadcast.utils",
        "com.pz.broadcast.logic"
})
public class BroadcastApplication {

	public static void main(String[] args) {
		SpringApplication.run(BroadcastApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return args -> storageService.init();
	}
}
