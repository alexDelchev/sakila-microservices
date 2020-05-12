package com.example.sakila.module.actor;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.module.actor.event.ActorEventUtils;
import com.example.sakila.module.actor.event.model.ActorCreatedEvent;
import com.example.sakila.module.actor.event.model.ActorDeletedEvent;
import com.example.sakila.module.actor.event.model.ActorEventDTO;
import com.example.sakila.module.actor.event.model.ActorUpdatedEvent;
import com.example.sakila.module.actor.repository.ActorRepository;
import com.example.sakila.exception.NotFoundException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

  private final Logger log = LoggerFactory.getLogger(ActorService.class);

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

  private void generateCreatedEvent(Actor actor) {
    ActorEventDTO dto = ActorEventUtils.toDTO(actor);
    ActorCreatedEvent event = new ActorCreatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  public Actor createActor(Actor actor) {
    log.info("Creating Actor");
    Actor result = actorRepository.insertActor(actor);
    log.info("Created Actor id: {}", actor.getId());

    generateCreatedEvent(result);

    return result;
  }

  private void generateUpdatedEvent(Actor actor) {
    ActorEventDTO dto = ActorEventUtils.toDTO(actor);
    ActorUpdatedEvent event = new ActorUpdatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  public Actor updateActor(String hexString, Actor source) {
    if (hexString == null || hexString.length() == 0) return null;
    ObjectId id = new ObjectId(hexString);
    return updateActor(id, source);
  }

  public Actor updateActor(ObjectId id, Actor source) {
    Actor target = actorRepository.getActorById(id);
    if (target == null) throw new NotFoundException("Actor for ID " + id + " does not exist");
    log.info("Updating actor (ID: {})", id.toHexString());

    target.setFirstName(source.getFirstName());
    target.setLastName(source.getLastName());

    Actor result = actorRepository.updateActor(target);

    generateUpdatedEvent(result);

    return result;
  }

  private void generateDeletedEvent(String hexString){
    ActorDeletedEvent event = new ActorDeletedEvent();
    event.setActorId(hexString);
    eventBus.emit(event);
  }

  public void deleteActor(String hexString) {
    if (hexString == null || hexString.length() == 0) return;
    ObjectId id = new ObjectId(hexString);
    deleteActor(id);
  }

  public void deleteActor(ObjectId id) {
    Actor actor = actorRepository.getActorById(id);
    if (actor == null) throw new NotFoundException("Actor for ID " + id + " does not exist");
    log.info("Deleting Actor (ID: {})", id.toHexString());

    try {
      actorRepository.deleteActor(actor);
      generateDeletedEvent(id.toHexString());
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
