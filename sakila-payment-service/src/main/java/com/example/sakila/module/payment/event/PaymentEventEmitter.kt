package com.example.sakila.module.payment.event

import com.example.sakila.event.bus.EventBus
import com.example.sakila.event.bus.Handler
import com.example.sakila.module.payment.event.model.PaymentCreatedEvent
import com.example.sakila.module.payment.event.model.PaymentDeletedEvent
import com.example.sakila.module.payment.event.model.PaymentUpdatedEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class PaymentEventEmitter @Autowired constructor(
    @Qualifier("PaymentEventBus")
    private val eventBus: EventBus,

    private val kafkaTemplate: KafkaTemplate<String, String>,

    private val objectMapper: ObjectMapper
) {

  private val paymentCreatedTopic = "sakila-payment-payment-created"

  private val paymentUpdatedTopic = "sakila-payment-payment-updated"

  private val paymentDeletedTopic = "sakila-payment-payment-deleted"

  @PostConstruct
  private fun postConstruct() {
    eventBus.register(component = this)
  }

  @Handler
  fun onPaymentCreatedEvent(event: PaymentCreatedEvent) {
    val serializedMessage = objectMapper.writeValueAsString(event)
    kafkaTemplate.send(paymentCreatedTopic, serializedMessage)
  }

  @Handler
  fun onPaymentUpdatedEvent(event: PaymentUpdatedEvent) {
    val serializedMessage = objectMapper.writeValueAsString(event)
    kafkaTemplate.send(paymentUpdatedTopic, serializedMessage)
  }

  @Handler
  fun onPaymentDeletedEvent(event: PaymentDeletedEvent) {
    val serializedMessage = objectMapper.writeValueAsString(event)
    kafkaTemplate.send(paymentDeletedTopic, serializedMessage)
  }
}
