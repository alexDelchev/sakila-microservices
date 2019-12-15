package com.example.sakila.module.film;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.film.repository.FilmRepository;
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

  @Test
  void updateFilm() {
    final long EXISTING_FILM_ID = 1L;
    when(filmRepository.getFilmById(EXISTING_FILM_ID)).thenReturn(new Film());

    final long NON_EXISTING_FILM_ID = -1L;
    when(filmRepository.getFilmById(NON_EXISTING_FILM_ID)).thenReturn(null);

    when(filmRepository.updateFilm(any(Film.class))).thenReturn(new Film());

    assertDoesNotThrow(() -> filmService.updateFilm(EXISTING_FILM_ID, new Film()));
    assertThrows(NotFoundException.class, () -> filmService.updateFilm(NON_EXISTING_FILM_ID, new Film()));
  }

  @Test
  void deleteFilm() {
    final long NON_EXISTING_ID = -1L;
    
    assertThrows(NotFoundException.class, () -> filmService.deleteFilm(NON_EXISTING_ID));
  }
}
