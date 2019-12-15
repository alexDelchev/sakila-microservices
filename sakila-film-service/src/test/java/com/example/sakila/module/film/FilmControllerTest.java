package com.example.sakila.module.film;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

  @Mock
  private FilmService filmService;

  @InjectMocks
  private FilmController filmController;

}
