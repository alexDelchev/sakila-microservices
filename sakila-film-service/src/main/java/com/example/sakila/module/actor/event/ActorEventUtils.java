package com.example.sakila.module.actor.event;

import com.example.sakila.module.actor.Actor;
import com.example.sakila.module.actor.event.model.ActorEventDTO;

public class ActorEventUtils {

  public static ActorEventDTO toDTO(Actor actor) {
    ActorEventDTO dto = new ActorEventDTO();

    dto.setId(actor.getId().toHexString());
    dto.setFirstName(actor.getFirstName());
    dto.setLastName(actor.getLastName());
    dto.setLastUpdate(actor.getLastUpdate());

    return dto;
  }
}
