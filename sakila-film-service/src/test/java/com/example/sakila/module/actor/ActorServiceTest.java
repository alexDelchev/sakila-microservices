package com.example.sakila.module.actor;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.actor.repository.ActorRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

  @Mock
  private ActorRepository actorRepository;

  @InjectMocks
  private ActorService actorService;

  @Test
  void getActorById() {
    String stringId = null;
    Actor actorByStringId = actorService.getActorById(stringId);

    ObjectId objectId = null;
    Actor actorByOjectId = actorService.getActorById(objectId);

    assertNull(actorByStringId);
    assertNull(actorByOjectId);
  }

  @Test
  void updateActor() {
    final ObjectId EXISTING_ACTOR_ID = new ObjectId();
    when(actorRepository.getActorById(EXISTING_ACTOR_ID)).thenReturn(new Actor());

    final ObjectId NON_EXISTING_ACTOR_ID = new ObjectId();
    when(actorRepository.getActorById(NON_EXISTING_ACTOR_ID)).thenReturn(null);

    when(actorRepository.updateActor(any(Actor.class))).thenReturn(new Actor());

    assertDoesNotThrow(() -> actorService.updateActor(EXISTING_ACTOR_ID, new Actor()));
    assertThrows(NotFoundException.class, () -> actorService.updateActor(NON_EXISTING_ACTOR_ID, new Actor()));
  }

  @Test
  void deleteActor() {
    final ObjectId NON_EXISTING_ACTOR_ID = new ObjectId();

    assertThrows(NotFoundException.class, () -> actorService.deleteActor(NON_EXISTING_ACTOR_ID));
  }
}
