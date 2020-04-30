package com.example.sakila.config;

import com.example.sakila.event.bus.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalEventInfrastructureConfig {

  @Bean
  @Qualifier("AddressEventBus")
  public EventBus addressEventBus() {
    return new EventBus("address-event-bus");
  }

  @Bean
  @Qualifier("CityEventBus")
  public EventBus cityEventBus() {
    return new EventBus("city-event-bus");
  }

  @Bean
  @Qualifier("CountryEventBus")
  public EventBus countryEventBus() {
    return new EventBus("country-event-bus");
  }
}
