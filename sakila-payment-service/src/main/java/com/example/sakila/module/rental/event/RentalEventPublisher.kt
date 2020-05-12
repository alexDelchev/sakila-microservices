package com.example.sakila.module.rental.event

import com.example.sakila.event.bus.EventBus
import com.example.sakila.event.bus.Handler
import com.example.sakila.module.rental.event.model.RentalCreatedEvent
import com.example.sakila.module.rental.event.model.RentalDeletedEvent
import com.example.sakila.module.rental.event.model.RentalUpdatedEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
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

  private val log = LoggerFactory.getLogger(RentalEventPublisher::class.java)

  @PostConstruct
  private fun postConstruct() {
    eventBus.register(this)
  }

  @Handler
  fun onRentalCreatedEvent(event: RentalCreatedEvent) = publish(rentalCreatedTopic, event)

  @Handler
  fun onRentalUpdatedEvent(event: RentalUpdatedEvent) = publish(rentalUpdatedTopic, event)

  @Handler
  fun onRentalDeletedEvent(event: RentalDeletedEvent) = publish(rentalDeletedTopic, event)

  private fun publish(topic: String, value: Any) {
    val serializedMessage = objectMapper.writeValueAsString(value)
    log.info("Publishing to ($topic): $serializedMessage")
    kafkaTemplate.send(topic, serializedMessage)
  }
}
