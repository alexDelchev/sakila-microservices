package com.example.sakila.module.rental.event

import com.example.sakila.event.bus.EventBus
import com.example.sakila.event.bus.Handler
import com.example.sakila.module.rental.event.model.RentalCreatedEvent
import com.example.sakila.module.rental.event.model.RentalDeletedEvent
import com.example.sakila.module.rental.event.model.RentalUpdatedEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class RentalEventPublisher @Autowired constructor(
    @Qualifier("RentalEventBus")
    private val eventBus: EventBus,

    private val kafkaTemplate: KafkaTemplate<String, String>,

    private val objectMapper: ObjectMapper
) {

  private val rentalCreatedTopic = "sakila-payment-rental-created"

  private val rentalUpdatedTopic = "sakila-payment-rental-updated"

  private val rentalDeletedTopic = "sakila-payment-rental-deleted"

  @PostConstruct
  private fun postConstruct() {
    eventBus.register(this)
  }

  @Handler
  fun onRentalCreatedEvent(event: RentalCreatedEvent) {
    val serializedMessage = objectMapper.writeValueAsString(event)
    kafkaTemplate.send(rentalCreatedTopic, serializedMessage)
  }

  @Handler
  fun onRentalUpdatedEvent(event: RentalUpdatedEvent) {
    val serializedMessage = objectMapper.writeValueAsString(event)
    kafkaTemplate.send(rentalUpdatedTopic, serializedMessage)
  }

  @Handler
  fun onRentalDeletedEvent(event: RentalDeletedEvent) {
    val serializedMessage = objectMapper.writeValueAsString(event)
    kafkaTemplate.send(rentalDeletedTopic, serializedMessage)
  }
}
