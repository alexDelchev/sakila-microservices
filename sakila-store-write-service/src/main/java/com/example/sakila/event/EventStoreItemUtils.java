package com.example.sakila.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventStoreItemUtils {

  private final static ObjectMapper mapper = new ObjectMapper();

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
