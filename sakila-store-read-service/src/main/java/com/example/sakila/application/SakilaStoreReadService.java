package com.example.sakila.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableScheduling
@ComponentScan("com.example.sakila")
public class SakilaStoreReadService {

  public static void main(String[] args) {
    SpringApplication.run(SakilaStoreReadService.class, args);
  }
}
