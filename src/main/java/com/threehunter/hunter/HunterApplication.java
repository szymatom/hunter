package com.threehunter.hunter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.threehunter.config" })
@EntityScan("com.threehunter.entity")
public class HunterApplication {
	public static void main(String[] args) {
		SpringApplication.run(HunterApplication.class, args);
	}
}
