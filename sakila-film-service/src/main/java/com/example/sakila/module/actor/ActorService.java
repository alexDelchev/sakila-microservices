package com.example.sakila.module.actor;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.module.actor.repository.ActorRepository;
import com.example.sakila.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

  public Actor addNewActor(Actor actor) {
    return actorRepository.insertActor(actor);
  }

  public Actor updateActor(Long id, Actor source) {
    Actor target = actorRepository.getActorById(id);
    if (target == null) throw new NotFoundException("Actor for ID " + id + " does not exist");

    target.setFirstName(source.getFirstName());
    target.setLastName(source.getLastName());

    return actorRepository.updateActor(target);
  }

  public void deleteActor(Long id) {
    Actor actor = actorRepository.getActorById(id);
    if (actor == null) throw new NotFoundException("Actor for ID " + id + " does not exist");

    try {
      actorRepository.deleteActor(actor);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
