package com.example.sakila.module.film.repository;

import com.example.sakila.module.film.Film;
import org.bson.types.ObjectId;

import java.util.List;

public interface FilmRepository {

  Film getFilmById(ObjectId id);

  List<Film> searchFilmsByTitle(String searchExpression);

  List<Film> searchFilmsByDescription(String searchExpression);

  List<Film> getFilmsByCategory(Long categoryId);

  List<Film> getFilmsByLanguage(Long languageId);

  List<Film> getFilmsByRating(String rating);

  Film insertFilm(Film film);

  Film updateFilm(Film film);

  void deleteFilm(Film film);
}
