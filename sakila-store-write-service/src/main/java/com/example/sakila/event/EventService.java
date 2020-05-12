package com.example.sakila.event;

import com.example.sakila.event.store.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

  private final Logger log = LoggerFactory.getLogger(EventService.class);

  private final EventStore eventStore;

  @Autowired
  public EventService(EventStore eventStore) {
    this.eventStore = eventStore;
  }

  public UUID getLatestEventId() {
    return eventStore.getLatestEventId();
  }

  public <T> Boolean aggregateExists(Long aggregateId, Class<T> type) {
    return eventStore.aggregateExists(aggregateId, type.getTypeName());
  }

  public Long persistAggregate(String type) {
    log.info("Persisting aggregate for type {}", type);
    Long id =  eventStore.persistAggregate(type, new Date());
    log.info("Persisted aggregate id: {}", id);

    return id;
  }

  public Long getAggregateVersion(Long aggregateId) {
    Long version = eventStore.getAggregateVersion(aggregateId);
    if (version == null) version = 0L;

    return version;
  }

  public void deleteAggregate(Long aggregateId) {
    log.info("Deleting aggregate (ID: {})", aggregateId);
    eventStore.deleteAggregate(aggregateId);
  }

  public <T> void persistEvent(Long aggregateId, Event<T> event) {
    EventStoreItem<T> item = new EventStoreItem<>();

    item.setAggregateId(aggregateId);
    item.setAggregateVersion(event.getVersion());
    item.setEventId(event.getId());
    item.setEvent(event);
    item.setMetaData(EventMetaData.forType(event.getClass()));
    item.setLastUpdate(new Date());

    EventStoreItemDatabaseDTO dto = EventStoreItemUtils.toDTO(item);

    log.info("Persisting event (ID: {}, type: {})", event.getId().toString(), event.getClass().getName());
    eventStore.persistEventStoreItem(dto);
  }

  public List<Event> getAllEvents() {
    return eventStore.getAllEvents()
        .stream()
        .map(dto -> EventStoreItemUtils.toEvent(dto, Object.class))
        .collect(Collectors.toList());
  }

  public <T> List<Event<T>> getEventsForAggregate(Long aggregateId, Class<T> type) {
    return eventStore.getEventStoreItemsForAggregate(aggregateId)
        .stream()
        .map(dto -> EventStoreItemUtils.toEvent(dto, type))
        .collect(Collectors.toList());
  }

  public <T> List<Event<T>> getEventsForAggregateUpToEvent(Long aggregateId, UUID eventId, Class<T> type) {
    return eventStore.getEventStoreItemsForAggregateUpToEvent(aggregateId, eventId)
        .stream()
        .map(dto -> EventStoreItemUtils.toEvent(dto, type))
        .collect(Collectors.toList());
  }

  public List<Event> getSubsequentEvents(UUID eventId) {
    return eventStore.getSubsequentEvents(eventId)
        .stream()
        .map(dto -> EventStoreItemUtils.toEvent(dto, Object.class))
        .collect(Collectors.toList());
  }

  public void deleteEventsForAggregate(Long aggregateId) {
    log.info("Deleting events for aggregate (ID: {})", aggregateId);
    eventStore.deleteEventStoreItemsForAggregate(aggregateId);
  }
}
