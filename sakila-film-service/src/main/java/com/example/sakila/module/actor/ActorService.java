package com.example.sakila.module.actor;

import com.example.sakila.module.actor.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

  private ActorRepository actorRepository;

  @Autowired
  public ActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public Actor getActorById(Long id) {
    if (id == null) return null;
    return actorRepository.getActorById(id);
  }

  public List<Actor> getActorsByFilmId(Long filmId) {
    if (filmId == null) return null;
    return actorRepository.getActorsByFilm(filmId);
  }
}
