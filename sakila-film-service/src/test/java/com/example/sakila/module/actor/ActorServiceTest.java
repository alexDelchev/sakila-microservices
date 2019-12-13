package com.example.sakila.module.actor;

import com.example.sakila.module.actor.repository.ActorRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

  @Mock
  private ActorRepository actorRepository;

  @InjectMocks
  private ActorService actorService;
}
