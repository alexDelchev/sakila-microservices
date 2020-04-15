package com.example.sakila.event;

import java.util.UUID;

public abstract class Event<T> {

  private UUID id = UUID.randomUUID();

  protected Long version;

  public abstract void apply(T t);

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
