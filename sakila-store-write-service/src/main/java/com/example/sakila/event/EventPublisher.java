package com.example.sakila.event;

import com.example.sakila.event.bus.EventBus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EventPublisher {

  private static final String LISTENER_GROUP_ID = "sakila-store-write-event-emitter";

  private static final String TRIGGER_EVENTS_TOPIC = "store-store-write-events-trigger";

  private final Logger log = LoggerFactory.getLogger(EventPublisher.class);

  private final ObjectMapper mapper;

  private final EventService eventService;

  private final EventBus storeEventBus;

  private final EventBus staffEventBus;

  @Autowired
  public EventPublisher(
      EventService eventService,
      @Qualifier("StoreEventBus") EventBus storeEventBus,
      @Qualifier("StaffEventBus") EventBus staffEventBus,
      ObjectMapper objectMapper
  ) {
    this.eventService = eventService;
    this.storeEventBus = storeEventBus;
    this.staffEventBus = staffEventBus;
    this.mapper = objectMapper;
  }

  @KafkaListener(topics = {TRIGGER_EVENTS_TOPIC}, id = LISTENER_GROUP_ID )
  public void triggerEvents(String message) {
    log.info("Consuming message from {}: {}", TRIGGER_EVENTS_TOPIC, message);
    EmitEventsMessage eventsMessage = deserialize(message, EmitEventsMessage.class);
    UUID latestEventId = eventService.getLatestEventId();

    if (latestEventId.equals(eventsMessage.getEventId())) return;
    log.info("Found stale read db state");

    if (eventsMessage.getEventId() != null) {
      log.info("Publishing events after {}", eventsMessage.getEventId().toString());
      emitSubsequentEvents(eventsMessage.getEventId());
    } else {
      log.info("Publishing all events");
      emitAllEvents();
    }
  }

  private void emitAllEvents() {
    eventService.getAllEvents().forEach(this::emitEvent);
  }

  private void emitSubsequentEvents(UUID eventId) {
    eventService.getSubsequentEvents(eventId).forEach(this::emitEvent);
  }

  private void emitEvent(Event event) {
    if (storeEventBus.hasHandlerForType(event.getClass())) {
      storeEventBus.emitSynchronously(event);
    } else if (staffEventBus.hasHandlerForType(event.getClass())) {
      staffEventBus.emitSynchronously(event);
    } else {
      throw new IllegalArgumentException(
          String.format("Could not find event bus for type %s", event.getClass().getTypeName())
      );
    }
  }

  private <T> T deserialize(String message, Class<T> type) {
    try {
      return mapper.readValue(message, type);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(String.format("Failed to parse json %s", message), e);
    }
  }
}
