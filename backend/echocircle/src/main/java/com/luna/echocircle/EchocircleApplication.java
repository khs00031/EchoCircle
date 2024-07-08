package com.luna.echocircle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class EchocircleApplication {

	public static void main(String[] args) {
		SpringApplication.run(EchocircleApplication.class, args);
		// User기능 개발할거임. Git 정상작동 test
	}

}
