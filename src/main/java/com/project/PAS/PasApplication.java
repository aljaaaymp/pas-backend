package com.project.PAS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@EnableAutoConfiguration
@SpringBootApplication
public class PasApplication {

	public static void main(String[] args) {
		try{
			SpringApplication.run(PasApplication.class, args);
		} catch (Throwable ex){
			ex.printStackTrace();
		}
	}

}
