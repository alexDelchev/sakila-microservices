package com.example.sakila.module.film;

import com.example.sakila.generated.server.api.FilmsApi;
import com.example.sakila.generated.server.model.FilmDTO;
import com.example.sakila.generated.server.model.FilmRating;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;

@Controller
public class FilmController implements FilmsApi {

  private final FilmService filmService;

  public FilmController(FilmService filmService ){
    this.filmService = filmService;
  }

  @Override
  public ResponseEntity<FilmDTO> getFilmById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(filmService.getFilmById(id)));
  }

  private FilmDTO toDTO(Film film) {
    FilmDTO filmDTO = new FilmDTO();
    filmDTO.setId(film.getId());
    filmDTO.setTitle(film.getTitle());
    filmDTO.setDescription(film.getDescription());
    filmDTO.setReleaseYear(film.getReleaseYear());
    if (film.getLanguage() != null) filmDTO.setLanguageId(film.getLanguage().getId());
    if (film.getOriginalLanguage() != null) filmDTO.setOriginalLanguageId(film.getOriginalLanguage().getId());
    filmDTO.setRentalDuration(film.getRentalDuration());
    filmDTO.setRentalRate(film.getRentalRate());
    filmDTO.setLength(film.getLength());
    filmDTO.setReplacementCost(film.getReplacementCost());
    filmDTO.setRating(FilmRating.fromValue(film.getRating()));
    if (film.getCategory() != null) filmDTO.setCategoryId(film.getCategory().getId());
    if (film.getSpecialFeatures() != null) filmDTO.setSpecialFeatures(Arrays.asList(film.getSpecialFeatures()));
    filmDTO.setLastUpdate(OffsetDateTime.ofInstant(film.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return filmDTO;
  }
}
