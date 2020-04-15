package com.example.sakila.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventStoreItemUtils {

  private final static ObjectMapper mapper = new ObjectMapper();

  static EventStoreItemDatabaseDTO toDTO(EventStoreItem item) {
    EventStoreItemDatabaseDTO result = new EventStoreItemDatabaseDTO();

    result.setEventId(item.getEventId());
    result.setAggregateId(item.getAggregateId());
    result.setAggregateVersion(item.getAggregateVersion());
    result.setEventJson(serialize(item.getEvent()));
    result.setMetaDataJson(serialize(item.getMetaData()));
    result.setLastUpdate(item.getLastUpdate());

    return result;
  }

  static <T> EventStoreItem<T> fromDTO(EventStoreItemDatabaseDTO dto, Class<T> rootType) {
    EventStoreItem<T> result = new EventStoreItem<>();

    EventMetaData metaData = deserialize(dto.getMetaDataJson(), EventMetaData.class);

    result.setEventId(dto.getEventId());
    result.setAggregateId(dto.getAggregateId());
    result.setAggregateVersion(dto.getAggregateVersion());
    result.setMetaData(metaData);
    result.setEvent(deserialize(dto.getEventJson(), metaData.getEventType(rootType)));
    result.setLastUpdate(dto.getLastUpdate());

    return result;
  }

  static <T> Event<T> toEvent(EventStoreItem<T> item) {
    return item.getEvent();
  }

  static <T> Event<T> toEvent(EventStoreItemDatabaseDTO dto, Class<T> rootType) {
    EventStoreItem<T> item = fromDTO(dto, rootType);
    return toEvent(item);
  }

  private static <T> T deserialize(String json, Class<T> type) {
    try {
      return mapper.readValue(json, type);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(String.format("Failed parsing json (%s)", json), e);
    }
  }

  private static String serialize(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(String.format("Failed to serialize: %s", e.getMessage()), e);
    }
  }
}
