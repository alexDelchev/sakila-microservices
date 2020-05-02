package com.example.sakila.config

import com.example.sakila.event.bus.EventBus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InternalEventInfrastructureConfig {

  @Bean
  @Qualifier('AddressEventBus')
  EventBus addressEventBus() {
    new EventBus('address-event-bus')
  }

  @Bean
  @Qualifier('CityEventBus')
  EventBus cityEventBus() {
    new EventBus('city-event-bus')
  }

  @Bean
  @Qualifier('CountryEventBus')
  EventBus countryEventBus() {
    new EventBus('country-event-bus')
  }
}
