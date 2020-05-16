package com.example.sakila.module.actor.event;

import com.example.sakila.module.actor.Actor;
import com.example.sakila.module.actor.event.model.ActorEventDTO;

import java.time.OffsetDateTime;
import java.time.ZoneId;

public class ActorEventUtils {

  public static ActorEventDTO toDTO(Actor actor) {
    ActorEventDTO dto = new ActorEventDTO();

    dto.setId(actor.getId().toHexString());
    dto.setFirstName(actor.getFirstName());
    dto.setLastName(actor.getLastName());

    if (actor.getLastUpdate() != null) {
      dto.setLastUpdate(OffsetDateTime.ofInstant(actor.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    }

    return dto;
  }
}
