package com.example.sakila.module.film.repository;

import com.example.sakila.module.film.Category;
import com.example.sakila.module.film.Film;
import com.example.sakila.module.film.Language;
import org.bson.types.ObjectId;

import java.util.List;

public interface FilmRepository {

  Film getFilmById(ObjectId id);

  List<Film> searchFilmsByTitle(String searchExpression);

  List<Film> searchFilmsByDescription(String searchExpression);

  List<Film> getFilmsByCategory(Category category);

  List<Film> getFilmsByLanguage(Language language);

  List<Film> getFilmsByRating(String rating);

  Film insertFilm(Film film);

  Film updateFilm(Film film);

  void deleteFilm(Film film);
}
