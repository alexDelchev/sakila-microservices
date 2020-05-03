package com.example.sakila.module.actor;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.actor.repository.ActorRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

  @Mock
  private EventBus eventBus = new EventBus("actor-test-event-bus");

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
    final ObjectId existingActorId = new ObjectId();
    when(actorRepository.getActorById(existingActorId)).thenReturn(new Actor(existingActorId));

    final ObjectId nonExistingActorId = new ObjectId();
    when(actorRepository.getActorById(nonExistingActorId)).thenReturn(null);

    when(actorRepository.updateActor(any(Actor.class))).thenReturn(new Actor(new ObjectId()));

    assertDoesNotThrow(() -> actorService.updateActor(existingActorId, new Actor()));
    assertThrows(NotFoundException.class, () -> actorService.updateActor(nonExistingActorId, new Actor()));
  }

  @Test
  void deleteActor() {
    final ObjectId nonExistingActorId = new ObjectId();

    assertThrows(NotFoundException.class, () -> actorService.deleteActor(nonExistingActorId));
  }
}
