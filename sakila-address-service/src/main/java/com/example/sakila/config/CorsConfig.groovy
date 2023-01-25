package com.example.sakila.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig {

  @Bean
  WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      void addCorsMappings(CorsRegistry registry) {
        registry.addMapping('/**')
            .allowedOrigins('*')
            .allowedHeaders('*')
            .allowedMethods('*')
      }
    }
  }
}
