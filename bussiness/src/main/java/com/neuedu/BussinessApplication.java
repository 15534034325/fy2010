package com.neuedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.neuedu.dao")
public class BussinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(BussinessApplication.class, args);
	}

}
