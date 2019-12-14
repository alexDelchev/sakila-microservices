package com.example.sakila.module.film.repository;

import com.example.sakila.module.film.Film;

import java.util.List;

public interface FilmRepository {

  Film getFilmById(Long id);

  List<Film> searchFilmsByTitle(String searchExpression);

  List<Film> searchFilmsByDescription(String searchExpression);

  List<Film> getFilmsByCategory(Long categoryId);

  List<Film> getFilmsByLanguage(Long languageId);

  List<Film> getFilmsByRating(String rating);

  Film insertFilm(Film film);
}
