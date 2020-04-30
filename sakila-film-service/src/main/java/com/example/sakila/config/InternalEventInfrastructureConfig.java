package com.example.sakila.config;

import com.example.sakila.event.bus.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalEventInfrastructureConfig {

  @Bean
  @Qualifier("FilmEventBus")
  public EventBus rentalEventBus() {
    return new EventBus("film-event-bus");
  }

  @Bean
  @Qualifier("ActorEventBus")
  public EventBus customerEventBus() {
    return new EventBus("actor-event-bus");
  }
}
