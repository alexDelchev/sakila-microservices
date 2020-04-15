package com.example.sakila.config;

import com.example.sakila.event.bus.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalEventInfrastructureConfig {

  @Bean
  @Qualifier("StaffEventBus")
  public EventBus staffEventBus() {
    return new EventBus("staff-event-bus");
  }

  @Bean
  @Qualifier("StoreEventBus")
  public EventBus storeEventBus() {
    return new EventBus("store-event-bus");
  }
}
