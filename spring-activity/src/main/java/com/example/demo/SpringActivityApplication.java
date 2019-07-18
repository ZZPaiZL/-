package com.example.demo;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@ComponentScan(basePackages = { "com.example.demo" })
//@MapperScan(basePackages = { "com.taikang.osms.dao" })
public class SpringActivityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringActivityApplication.class, args);
	}

}
