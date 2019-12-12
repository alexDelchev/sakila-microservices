package com.example.sakila.module.actor.repository;

import com.example.sakila.module.actor.Actor;

import java.util.List;

public interface ActorRepository {

  Actor getActorById(Long id);

  List<Actor> getActorsByFilm(Long filmId);

  Actor insertActor(Actor actor);
}
