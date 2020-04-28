package com.example.sakila.event

import com.example.sakila.event.bus.EventBus
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class EventEmitterTest extends Specification {

  private final ObjectMapper objectMapper = new ObjectMapper()

  private final EventService eventService = Mock(EventService)

  private final EventBus storeEventBus = Mock(EventBus)

  private final EventBus staffEventBus = Mock(EventBus)

  private final EventEmitter eventEmitter = new EventEmitter(eventService, storeEventBus, staffEventBus)

  def "should get subsequent events when event id is provided"() {
    given:
    UUID eventId = UUID.randomUUID()

    EmitEventsMessage message = new EmitEventsMessage(eventId: eventId)
    String serializedMessage = objectMapper.writeValueAsString(message)

    when:
    eventEmitter.triggerEvents(serializedMessage)

    then:
    1 * eventService.getSubsequentEvents(_) >> []
  }

  def "should get all events when no id is provided"() {
    given:
    UUID eventId = null

    EmitEventsMessage message = new EmitEventsMessage(eventId: eventId)
    String serializedMessage = objectMapper.writeValueAsString(message)

    when:
    eventEmitter.triggerEvents(serializedMessage)

    then:
    1 * eventService.getAllEvents() >> []
  }
}
