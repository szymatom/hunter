package com.threehunter.hunter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.threehunter.config" })
public class HunterApplication {
	public static void main(String[] args) {
		SpringApplication.run(HunterApplication.class, args);
	}
}
