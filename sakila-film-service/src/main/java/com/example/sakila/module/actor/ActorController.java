package com.example.sakila.module.actor;

import com.example.sakila.generated.server.api.ActorsApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ActorController implements ActorsApi {

  private final ActorService actorService;

  @Autowired
  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }
}
