package com.example.sakila.event.store;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface EventStore {

  Boolean aggregateExists(Long aggregateid, String type);

  Long persistAggregate(String type, Date date);

  Long getAggregateVersion(Long aggregateId);

  void deleteAggregate(Long aggregateId);

  void persistEventStoreItem(EventStoreItemDatabaseDTO item);

  List<EventStoreItemDatabaseDTO> getAllEvents();

  List<EventStoreItemDatabaseDTO> getEventStoreItemsForAggregate(Long aggregateId);

  List<EventStoreItemDatabaseDTO> getEventStoreItemsForAggregateUpToEvent(Long aggregateId, UUID eventId);

  List<EventStoreItemDatabaseDTO> getSubsequentEvents(UUID eventId);

  void deleteEventStoreItemsForAggregate(Long aggregateId);

}
