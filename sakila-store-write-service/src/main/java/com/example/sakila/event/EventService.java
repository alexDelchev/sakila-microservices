package com.example.sakila.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

  private final EventStore eventStore;

  @Autowired
  public EventService(EventStore eventStore) {
    this.eventStore = eventStore;
  }

  public Long persistAggregate(String type) {
    return eventStore.persistAggregate(type, new Date());
  }

  public Long getAggregateVersion(Long aggregateId) {
    Long version = eventStore.getAggregateVersion(aggregateId);
    if (version == null) version = 0L;

    return version;
  }
}
