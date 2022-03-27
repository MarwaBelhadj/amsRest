package com.sip.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AmsApiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(AmsApiApplication.class, args);
	}

}
