package com.example.sakila.event;

public class EventMetaData {

  private String typeName;

  public EventMetaData() {}

  public EventMetaData(Class type) {

    setTypeName(type.getTypeName());
  }

  public static EventMetaData forType(Class type) {
    return new EventMetaData(type);
  }

  public <T> Class<Event<T>> getEventType(Class<T> rootType) {
    try {
      return (Class<Event<T>>) Class.forName(typeName).asSubclass(Event.class);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(String.format("Failed to load class for %s", typeName), e);
    }
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }
}
