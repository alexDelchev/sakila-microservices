package com.example.sakila.module.actor;

import com.example.sakila.generated.server.model.ActorDTO;
import org.bson.types.ObjectId;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ActorUtils {

  public static ActorDTO toDTO(Actor actor) {
    ActorDTO actorDTO = new ActorDTO();
    actorDTO.setId(actor.getId().toHexString());
    actorDTO.setFirstName(actor.getFirstName());
    actorDTO.setLastName(actor.getLastName());
    actorDTO.setLastUpdate(actor.getLastUpdate());
    return actorDTO;
  }

  public static Actor toEntity(ActorDTO actorDTO) {
    Actor actor = new Actor();
    actor.setId(new ObjectId(actorDTO.getId()));
    actor.setFirstName(actorDTO.getFirstName());
    actor.setLastName(actorDTO.getLastName());
    actor.setLastUpdate(actorDTO.getLastUpdate());
    return actor;
  }

}
