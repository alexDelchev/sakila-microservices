package com.example.sakila.module.customer.event

import com.example.sakila.event.bus.EventBus
import com.example.sakila.event.bus.Handler
import com.example.sakila.module.customer.event.model.CustomerCreatedEvent
import com.example.sakila.module.customer.event.model.CustomerDeletedEvent
import com.example.sakila.module.customer.event.model.CustomerUpdatedEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class CustomerEventPublisher @Autowired constructor(
    @Qualifier("CustomerEventBus")
    private val eventBus: EventBus,

    private val kafkaTemplate: KafkaTemplate<String, String>,

    private val objectMapper: ObjectMapper
) {

  private val customerCreatedTopic = "sakila-payment-customer-created"

  private val customerUpdatedTopic = "sakila-payment-customer-updated"

  private val customerDeletedTopic = "sakila-payment-customer-deleted"

  private val log = LoggerFactory.getLogger(CustomerEventPublisher::class.java)

  @PostConstruct
  private fun postConstruct() {
    eventBus.register(component = this)
  }

  @Handler
  fun onCustomerCreatedEvent(event: CustomerCreatedEvent) = publish(customerCreatedTopic, event)

  @Handler
  fun onCustomerUpdatedEvent(event: CustomerUpdatedEvent) = publish(customerUpdatedTopic, event)

  @Handler
  fun onCustomerDeletedEvent(event: CustomerDeletedEvent) = publish(customerDeletedTopic, event)

  private fun publish(topic: String, value: Any) {
    val serializedMessage = objectMapper.writeValueAsString(value)
    log.info("Publishing to ($topic): $serializedMessage")
    kafkaTemplate.send(topic, serializedMessage)
  }
}
