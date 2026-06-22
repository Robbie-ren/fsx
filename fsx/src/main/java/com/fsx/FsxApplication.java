package com.fsx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class FsxApplication {

	public static void main(String[] args) {

		SpringApplication.run(FsxApplication.class, args);
	}

}
