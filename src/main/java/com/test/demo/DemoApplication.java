package com.test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		//сохраняющее их в базу данных и
		// предоставляющее возможности
		// по просмотру и поиску резюме.
		SpringApplication.run(DemoApplication.class, args);
	}
}
