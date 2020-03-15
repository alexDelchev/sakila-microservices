package com.example.sakila.module.film;

import com.example.sakila.generated.server.model.FilmDTO;

import java.sql.Date;

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
    film.setCategoryId(filmDTO.getCategoryId());
    if (filmDTO.getSpecialFeatures() != null) {
      film.setSpecialFeatures(filmDTO.getSpecialFeatures().toArray(new String[0]));
    }
    if (filmDTO.getLastUpdate() != null) film.setLastUpdate(Date.from(filmDTO.getLastUpdate().toInstant()));
    return film;
  }
}
