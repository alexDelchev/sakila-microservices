package com.example.sakila.module.actor.event.model;

public class ActorDeletedEvent {

  private String actorId;

  public String getActorId() {
    return actorId;
  }

  public void setActorId(String actorId) {
    this.actorId = actorId;
  }
}
