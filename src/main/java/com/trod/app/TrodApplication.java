package com.trod.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan("com.trod")
public class TrodApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrodApplication.class, args);
	}

}
