package com.example.sakila.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.example.sakila")
public class SakilaStoreService {

  public static void main(String[] args) {
    SpringApplication.run(SakilaStoreService.class, args);
  }
}
