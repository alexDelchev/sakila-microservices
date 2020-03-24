package com.example.sakila.module.film;

import com.example.sakila.generated.server.model.ApiFilmCategory;
import com.example.sakila.generated.server.model.ApiFilmLanguage;
import com.example.sakila.generated.server.model.FilmDTO;
import com.example.sakila.generated.server.model.FilmRating;
import org.bson.types.ObjectId;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilmUtils {

  public static Film toEntity(FilmDTO filmDTO) {
    Film film = new Film();

    film.setId(new ObjectId(filmDTO.getId()));
    film.setTitle(filmDTO.getTitle());
    film.setDescription(filmDTO.getDescription());
    film.setReleaseYear(filmDTO.getReleaseYear());
    film.setRentalDuration(filmDTO.getRentalDuration());
    film.setRentalRate(filmDTO.getRentalRate());
    film.setLength(filmDTO.getLength());
    film.setReplacementCost(filmDTO.getReplacementCost());

    if (filmDTO.getLanguages() != null) {
      List<Language> languages = filmDTO.getLanguages()
          .stream()
          .filter(Objects::nonNull)
          .map(l -> Language.valueOf(l.toString()))
          .collect(Collectors.toList());
      film.setLanguages(languages);
    }

    if (filmDTO.getOriginalLanguages() != null) {
      List<Language> originalLanguages = filmDTO.getOriginalLanguages()
          .stream()
          .filter(Objects::nonNull)
          .map(l -> Language.valueOf(l.toString()))
          .collect(Collectors.toList());
      film.setOriginalLanguages(originalLanguages);
    }

    if (filmDTO.getCategories() != null) {
      List<Category> categories = filmDTO.getCategories()
          .stream()
          .filter(Objects::nonNull)
          .map(c -> Category.valueOf(c.toString()))
          .collect(Collectors.toList());
      film.setCategories(categories);
    }

    if (filmDTO.getRating() != null) film.setRating(filmDTO.getRating().toString());
    if (filmDTO.getSpecialFeatures() != null) {
      film.setSpecialFeatures(filmDTO.getSpecialFeatures());
    }
    if (filmDTO.getLastUpdate() != null) film.setLastUpdate(Date.from(filmDTO.getLastUpdate().toInstant()));
    return film;
  }

  public static FilmDTO toDTO(Film film) {
    FilmDTO filmDTO = new FilmDTO();

    filmDTO.setId(film.getId().toHexString());
    filmDTO.setTitle(film.getTitle());
    filmDTO.setDescription(film.getDescription());
    filmDTO.setReleaseYear(film.getReleaseYear());
    filmDTO.setRentalDuration(film.getRentalDuration());
    filmDTO.setRentalRate(film.getRentalRate());
    filmDTO.setLength(film.getLength());
    filmDTO.setReplacementCost(film.getReplacementCost());
    filmDTO.setRating(FilmRating.fromValue(film.getRating()));

    if (film.getLanguages() != null) {
      List<ApiFilmLanguage> languages = film.getLanguages()
          .stream()
          .filter(Objects::nonNull)
          .map(l -> ApiFilmLanguage.fromValue(l.toString()))
          .collect(Collectors.toList());
      filmDTO.setLanguages(languages);
    }

    if (film.getOriginalLanguages() != null ) {
      List<ApiFilmLanguage> originalLanguages = film.getOriginalLanguages()
          .stream()
          .filter(Objects::nonNull)
          .map(l -> ApiFilmLanguage.fromValue(l.toString()))
          .collect(Collectors.toList());
      filmDTO.setOriginalLanguages(originalLanguages);
    }

    if (film.getCategories() != null) {
      List<ApiFilmCategory> categories = film.getCategories()
          .stream()
          .filter(Objects::nonNull)
          .map(c -> ApiFilmCategory.fromValue(c.toString()))
          .collect(Collectors.toList());
      filmDTO.setCategories(categories);
    }

    if (film.getSpecialFeatures() != null) filmDTO.setSpecialFeatures(film.getSpecialFeatures());
    filmDTO.setLastUpdate(OffsetDateTime.ofInstant(film.getLastUpdate().toInstant(), ZoneId.systemDefault()));

    return filmDTO;
  }
}
