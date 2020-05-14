package com.threehunter.hunter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.threehunter.config" })
@EnableJpaRepositories("com.threehunter.repository")
@EntityScan("com.threehunter.entity")
public class HunterApplication {
	public static void main(String[] args) {
		SpringApplication.run(HunterApplication.class, args);
	}
}
