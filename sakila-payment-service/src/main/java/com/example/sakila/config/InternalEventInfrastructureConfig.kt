package com.example.sakila.config

import com.example.sakila.event.bus.EventBus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InternalEventInfrastructureConfig {

  @Bean
  @Qualifier("RentalEventBus")
  fun rentalEventBus(): EventBus = EventBus("rental-event-bus")

  @Bean
  @Qualifier("CustomerEventBus")
  fun customerEventBus(): EventBus = EventBus("customer-event-bus")

  @Bean
  @Qualifier("PaymentEventBus")
  fun paymentEventBus(): EventBus = EventBus("payment-event-bus")
}
