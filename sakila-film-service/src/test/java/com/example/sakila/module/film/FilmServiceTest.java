package com.example.sakila.module.film;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.film.repository.FilmRepository;
import org.bson.types.ObjectId;
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
  private EventBus eventBus;

  @Mock
  private FilmRepository filmRepository;

  @InjectMocks
  private FilmService filmService;

  @Test
  void getFilmById() {
    String stringId = null;
    Film filmByStringId = filmService.getFilmById(stringId);

    ObjectId objectId = null;
    Film filmByObjectId = filmService.getFilmById(objectId);

    assertNull(filmByStringId);
    assertNull(filmByObjectId);
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
  void getFilmsByRating() {
    List<Film> films = filmService.getFilmsByRating(null);

    assertNull(films);
  }

  @Test
  void updateFilm() {
    final ObjectId existingFilmId = new ObjectId();
    when(filmRepository.getFilmById(existingFilmId)).thenReturn(new Film(existingFilmId));

    final ObjectId nonExistingFilmId = new ObjectId();
    when(filmRepository.getFilmById(nonExistingFilmId)).thenReturn(null);

    when(filmRepository.updateFilm(any(FilmWriteModel.class))).thenReturn(filmWriteModel());

    assertDoesNotThrow(() -> filmService.updateFilm(existingFilmId, new Film()));
    assertThrows(NotFoundException.class, () -> filmService.updateFilm(nonExistingFilmId, new Film()));
  }

  private FilmWriteModel filmWriteModel() {
    FilmWriteModel model = new FilmWriteModel();
    model.setId(new ObjectId());
    return model;
  }

  @Test
  void deleteFilm() {
    final ObjectId nonExistingId = new ObjectId();

    assertThrows(NotFoundException.class, () -> filmService.deleteFilm(nonExistingId));
  }
}
