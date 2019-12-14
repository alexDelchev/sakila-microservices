package com.example.sakila.module.film;

import com.example.sakila.module.film.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FilmService {

  private final FilmRepository filmRepository;

  @Autowired
  public FilmService(FilmRepository filmRepository) {
    this.filmRepository = filmRepository;
  }

  public Film getFilmById(Long id) {
    if (id == null) return null;
    return filmRepository.getFilmById(id);
  }

  public List<Film> searchFilmsByTitle(String searchExpression) {
    if (searchExpression == null) return null;
    return filmRepository.searchFilmsByTitle(searchExpression);
  }

  public List<Film> searchFilmsByDescription(String searchExpression) {
    if (searchExpression == null) return null;
    return filmRepository.searchFilmsByDescription(searchExpression);
  }

  public List<Film> getFilmsByCategoryId(Long categoryId) {
    if (categoryId == null) return null;
    return filmRepository.getFilmsByCategory(categoryId);
  }

  public List<Film> getFilmsByLanguageId(Long languageId) {
    if (languageId == null) return null;
    return filmRepository.getFilmsByLanguage(languageId);
  }

  public List<Film> getFilmsByRating(String rating) {
    if (rating == null) return null;
    return filmRepository.getFilmsByRating(rating);
  }

  public Film createFilm(Film film) {
    return filmRepository.insertFilm(film);
  }
}
