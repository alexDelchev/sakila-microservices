package com.example.sakila.module.film;

import com.example.sakila.generated.server.model.FilmDTO;
import com.example.sakila.generated.server.model.FilmRating;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class FilmUtils {

  public static Film toEntity(FilmDTO filmDTO) {
    Film film = new Film();

    film.setId(filmDTO.getId());
    film.setTitle(filmDTO.getTitle());
    film.setDescription(filmDTO.getDescription());
    film.setReleaseYear(filmDTO.getReleaseYear());
    film.setLanguageId(filmDTO.getLanguageId());
    film.setOriginalLanguageId(filmDTO.getOriginalLanguageId());
    film.setRentalDuration(filmDTO.getRentalDuration());
    film.setRentalRate(filmDTO.getRentalRate());
    film.setLength(filmDTO.getLength());
    film.setReplacementCost(filmDTO.getReplacementCost());
    if (filmDTO.getRating() != null) film.setRating(filmDTO.getRating().toString());
    film.setCategoryIds(filmDTO.getCategoryIds());
    if (filmDTO.getSpecialFeatures() != null) {
      film.setSpecialFeatures(filmDTO.getSpecialFeatures());
    }
    if (filmDTO.getLastUpdate() != null) film.setLastUpdate(Date.from(filmDTO.getLastUpdate().toInstant()));
    return film;
  }

  public static FilmDTO toDTO(Film film) {
    FilmDTO filmDTO = new FilmDTO();

    filmDTO.setId(film.getId());
    filmDTO.setTitle(film.getTitle());
    filmDTO.setDescription(film.getDescription());
    filmDTO.setReleaseYear(film.getReleaseYear());
    filmDTO.setLanguageId(film.getLanguageId());
    filmDTO.setOriginalLanguageId(film.getOriginalLanguageId());
    filmDTO.setRentalDuration(film.getRentalDuration());
    filmDTO.setRentalRate(film.getRentalRate());
    filmDTO.setLength(film.getLength());
    filmDTO.setReplacementCost(film.getReplacementCost());
    filmDTO.setRating(FilmRating.fromValue(film.getRating()));
    filmDTO.setCategoryIds(film.getCategoryIds());
    if (film.getSpecialFeatures() != null) filmDTO.setSpecialFeatures(film.getSpecialFeatures());
    filmDTO.setLastUpdate(OffsetDateTime.ofInstant(film.getLastUpdate().toInstant(), ZoneId.systemDefault()));

    return filmDTO;
  }
}
