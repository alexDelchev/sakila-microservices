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
  public ResponseEntity<ActorDTO> getActorById(@PathVariable("id") String id) {
    return ResponseEntity.ok(ActorUtils.toDTO(actorService.getActorById(id)));
  }

  @Override
  public ResponseEntity<ActorDTO> createActor(@RequestBody ActorDTO actorDTO) {
    return ResponseEntity.ok(ActorUtils.toDTO(actorService.createActor(ActorUtils.toEntity(actorDTO))));
  }

  @Override
  public ResponseEntity<ActorDTO> replaceActor(@PathVariable("id") String id, @RequestBody ActorDTO actorDTO) {
    return ResponseEntity.ok(ActorUtils.toDTO(actorService.updateActor(id, ActorUtils.toEntity(actorDTO))));
  }

  @Override
  public ResponseEntity<Void> deleteActor(@PathVariable("id") String id) {
    actorService.deleteActor(id);
    return  ResponseEntity.ok(null);
  }

}
