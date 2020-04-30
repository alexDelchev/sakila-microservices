package com.example.sakila.module.actor;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.module.actor.repository.ActorRepository;
import com.example.sakila.exception.NotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

  private final EventBus eventBus;

  private ActorRepository actorRepository;

  @Autowired
  public ActorService(@Qualifier("ActorEventBus") EventBus eventBus, ActorRepository actorRepository) {
    this.eventBus = eventBus;
    this.actorRepository = actorRepository;
  }

  public Actor getActorById(String hexString) {
    if (hexString == null || hexString.length() == 0) return null;
    ObjectId id = new ObjectId(hexString);
    return getActorById(id);
  }

  public Actor getActorById(ObjectId id) {
    if (id == null) return null;
    return actorRepository.getActorById(id);
  }

  public Actor createActor(Actor actor) {
    return actorRepository.insertActor(actor);
  }

  public Actor updateActor(String hexString, Actor source) {
    if (hexString == null || hexString.length() == 0) return null;
    ObjectId id = new ObjectId(hexString);
    return updateActor(id, source);
  }

  public Actor updateActor(ObjectId id, Actor source) {
    Actor target = actorRepository.getActorById(id);
    if (target == null) throw new NotFoundException("Actor for ID " + id + " does not exist");

    target.setFirstName(source.getFirstName());
    target.setLastName(source.getLastName());

    return actorRepository.updateActor(target);
  }

  public void deleteActor(String hexString) {
    if (hexString == null || hexString.length() == 0) return;
    ObjectId id = new ObjectId(hexString);
    deleteActor(id);
  }

  public void deleteActor(ObjectId id) {
    Actor actor = actorRepository.getActorById(id);
    if (actor == null) throw new NotFoundException("Actor for ID " + id + " does not exist");

    try {
      actorRepository.deleteActor(actor);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
