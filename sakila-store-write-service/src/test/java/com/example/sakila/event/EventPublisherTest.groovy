package com.example.sakila.event

import com.example.sakila.event.bus.EventBus
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class EventPublisherTest extends Specification {

  private final ObjectMapper objectMapper = new ObjectMapper()

  private final EventService eventService = Mock(EventService)

  private final EventBus storeEventBus = Mock(EventBus)

  private final EventBus staffEventBus = Mock(EventBus)

  private final EventPublisher eventEmitter = new EventPublisher(
      eventService, storeEventBus, staffEventBus, objectMapper
  )

  def "should get subsequent events when event id is provided"() {
    given:
    UUID eventId = UUID.randomUUID()
    UUID latestEventId = UUID.randomUUID()
    eventService.getLatestEventId() >> latestEventId

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
    UUID latestEventId = UUID.randomUUID()
    eventService.getLatestEventId() >> latestEventId

    EmitEventsMessage message = new EmitEventsMessage(eventId: eventId)
    String serializedMessage = objectMapper.writeValueAsString(message)

    when:
    eventEmitter.triggerEvents(serializedMessage)

    then:
    1 * eventService.getAllEvents() >> []
  }

  def "should not fetch events when broadcasted event id is the same as the latest"() {
    given:
    UUID eventId = UUID.randomUUID()
    eventService.getLatestEventId() >> UUID.fromString(eventId.toString())

    EmitEventsMessage message = new EmitEventsMessage(eventId: eventId)
    String serializedMessage = objectMapper.writeValueAsString(message)

    when:
    eventEmitter.triggerEvents(serializedMessage)

    then:
    0 * eventService.getAllEvents()
    0 * eventService.getSubsequentEvents(_)
  }
}
