package com.example.sakila.module.payment.event

import com.example.sakila.event.bus.EventBus
import com.example.sakila.event.bus.Handler
import com.example.sakila.module.payment.event.model.PaymentCreatedEvent
import com.example.sakila.module.payment.event.model.PaymentDeletedEvent
import com.example.sakila.module.payment.event.model.PaymentUpdatedEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class PaymentEventPublisher @Autowired constructor(
    @Qualifier("PaymentEventBus")
    private val eventBus: EventBus,

    private val kafkaTemplate: KafkaTemplate<String, String>,

    private val objectMapper: ObjectMapper
) {

  private val paymentCreatedTopic = "sakila-payment-payment-created"

  private val paymentUpdatedTopic = "sakila-payment-payment-updated"

  private val paymentDeletedTopic = "sakila-payment-payment-deleted"

  private val log = LoggerFactory.getLogger(PaymentEventPublisher::class.java)

  @PostConstruct
  private fun postConstruct() {
    eventBus.register(component = this)
  }

  @Handler
  fun onPaymentCreatedEvent(event: PaymentCreatedEvent) = publish(paymentCreatedTopic, event)

  @Handler
  fun onPaymentUpdatedEvent(event: PaymentUpdatedEvent) = publish(paymentUpdatedTopic, event)

  @Handler
  fun onPaymentDeletedEvent(event: PaymentDeletedEvent) = publish(paymentDeletedTopic, event)

  private fun publish(topic: String, value: Any) {
    val serializedMessage = objectMapper.writeValueAsString(value)
    log.info("Publishing to ($topic): $serializedMessage")
    kafkaTemplate.send(topic, serializedMessage)
  }
}
