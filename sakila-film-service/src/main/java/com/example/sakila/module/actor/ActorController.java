package com.example.sakila.module.actor;

import com.example.sakila.generated.server.api.ActorsApi;
import com.example.sakila.generated.server.model.ActorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

  @Override
  public ResponseEntity<List<ActorDTO>> getActorsByFilmId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        actorService.getActorsByFilmId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<ActorDTO> addNewActor(@RequestBody ActorDTO actorDTO) {
    return ResponseEntity.ok(toDTO(actorService.addNewActor(toEntity(actorDTO))));
  }

  @Override
  public ResponseEntity<ActorDTO> replaceActor(@PathVariable("id") Long id, @RequestBody ActorDTO actorDTO) {
    return ResponseEntity.ok(toDTO(actorService.updateActor(id, toEntity(actorDTO))));
  }

  @Override
  public ResponseEntity<Void> deleteActor(@PathVariable("id") Long id) {
    actorService.deleteActor(id);
    return  ResponseEntity.ok(null);
  }

  private ActorDTO toDTO(Actor actor) {
    ActorDTO actorDTO = new ActorDTO();
    actorDTO.setId(actor.getId());
    actorDTO.setFirstName(actor.getFirstName());
    actorDTO.setLastName(actor.getLastName());
    actorDTO.setLastUpdate(OffsetDateTime.ofInstant(actor.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return actorDTO;
  }

  private Actor toEntity(ActorDTO actorDTO) {
    Actor actor = new Actor();
    actor.setId(actorDTO.getId());
    actor.setFirstName(actorDTO.getFirstName());
    actor.setLastName(actorDTO.getLastName());
    if (actorDTO.getLastUpdate() != null) actor.setLastUpdate(Date.from(actorDTO.getLastUpdate().toInstant()));
    return actor;
  }

}
