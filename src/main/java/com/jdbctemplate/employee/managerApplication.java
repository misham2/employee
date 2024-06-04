package com.jdbctemplate.employee;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableEncryptableProperties
@SpringBootApplication
public class managerApplication {

	public static void main(String[] args) {
		SpringApplication.run(managerApplication.class, args);
	}

}
