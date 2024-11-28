package com.ilroberts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class JavaModulithApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaModulithApplication.class, args);
	}

}
