package com.example.sakila.module.actor.repository;

import com.example.sakila.module.actor.Actor;
import org.bson.types.ObjectId;

import java.util.List;

public interface ActorRepository {

  Actor getActorById(ObjectId id);

  Actor insertActor(Actor actor);

  Actor updateActor(Actor actor);

  void deleteActor(Actor actor);
}
