package com.example.sakila.event.store;

import java.util.Date;
import java.util.List;

public interface EventStore {

  Boolean aggregateExists(Long aggregateid, String type);

  Long persistAggregate(String type, Date date);

  Long getAggregateVersion(Long aggregateId);

  void deleteAggregate(Long aggregateId);

  void persistEventStoreItem(EventStoreItemDatabaseDTO item);

  List<EventStoreItemDatabaseDTO> getEventStoreItemsForAggregate(Long aggregateId);

  void deleteEventStoreItemsForAggregate(Long aggregateId);

}
