package com.example.sakila.module.actor.event.model;

public class ActorCreatedEvent {

  private ActorEventDTO dto;

  public ActorEventDTO getDto() {
    return dto;
  }

  public void setDto(ActorEventDTO dto) {
    this.dto = dto;
  }
}
