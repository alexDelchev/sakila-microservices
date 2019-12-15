package com.example.sakila.module.film;

import com.example.sakila.module.film.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

  @Test
  void searchFilmsByTitle() {
    List<Film> films = filmService.searchFilmsByTitle(null);

    assertNull(films);
  }

  @Test
  void searchFilmsByDescription() {
    List<Film> films = filmService.searchFilmsByDescription(null);

    assertNull(films);
  }

  @Test
  void getFilmsByCategoryId() {
    List<Film> films = filmService.getFilmsByCategoryId(null);

    assertNull(films);
  }

  @Test
  void getFilmsByLanguageId() {
    List<Film> films = filmService.getFilmsByLanguageId(null);

    assertNull(films);
  }

  @Test
  void getFilmsByRating() {
    List<Film> films = filmService.getFilmsByRating(null);

    assertNull(films);
  }
}
