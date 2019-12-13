package com.example.sakila.module.actor;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.actor.repository.ActorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
    Actor actor = actorService.getActorById(null);

    assertNull(actor);
  }

  @Test
  void getActorsByFilmId() {
    List<Actor> actors = actorService.getActorsByFilmId(null);

    assertNull(actors);
  }

  @Test
  void updateActor() {
    final long EXISTING_ACTOR_ID = 1L;
    when(actorRepository.getActorById(EXISTING_ACTOR_ID)).thenReturn(new Actor());

    final long NON_EXISTING_ACTOR_ID = -1L;
    when(actorRepository.getActorById(NON_EXISTING_ACTOR_ID)).thenReturn(null);

    when(actorRepository.updateActor(any(Actor.class))).thenReturn(new Actor());

    assertDoesNotThrow(() -> actorService.updateActor(EXISTING_ACTOR_ID, new Actor()));
    assertThrows(NotFoundException.class, () -> actorService.updateActor(NON_EXISTING_ACTOR_ID, new Actor()));
  }

  @Test
  void deleteActor() {
    final long NON_EXISTING_ACTOR_ID = -1L;

    assertThrows(NotFoundException.class, () -> actorService.deleteActor(NON_EXISTING_ACTOR_ID));
  }
}
