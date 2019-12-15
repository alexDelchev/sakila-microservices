package com.example.sakila.module.film;

import com.example.sakila.module.film.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

  @Mock
  private FilmRepository filmRepository;

  @InjectMocks
  private FilmService filmService;

  @Test
  void getFilmById() {
    Film film = filmService.getFilmById(null);

    assertNull(film);
  }
}
