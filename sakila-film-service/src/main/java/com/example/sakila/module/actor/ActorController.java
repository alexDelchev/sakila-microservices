package com.example.sakila.module.actor;

import com.example.sakila.generated.server.api.ActorsApi;
import com.example.sakila.generated.server.model.ActorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Controller
public class ActorController implements ActorsApi {

  private final ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @Override
  public ResponseEntity<ActorDTO> getActorById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        toDTO(actorService.getActorById(id))
    );
  }

  private ActorDTO toDTO(Actor actor) {
    ActorDTO actorDTO = new ActorDTO();
    actorDTO.setId(actor.getId());
    actorDTO.setFirstName(actor.getFirstName());
    actorDTO.setLastName(actor.getLastName());
    actorDTO.setLastUpdate(OffsetDateTime.ofInstant(actor.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return actorDTO;
  }

}
