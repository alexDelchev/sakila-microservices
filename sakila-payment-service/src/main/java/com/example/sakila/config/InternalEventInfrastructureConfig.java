package com.example.sakila.config;

import com.example.sakila.event.bus.EventBus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InternalEventInfrastructureConfig {

  @Bean
  @Qualifier("RentalEventBus")
  public EventBus rentalEventBus() {
    return new EventBus("rental-event-bus");
  }

  @Bean
  @Qualifier("CustomerEventBus")
  public EventBus customerEventBus() {
    return new EventBus("customer-event-bus");
  }

  @Bean
  @Qualifier("PaymentEventBus")
  public EventBus paymentEventBus() {
    return new EventBus("payment-event-bus");
  }
}
