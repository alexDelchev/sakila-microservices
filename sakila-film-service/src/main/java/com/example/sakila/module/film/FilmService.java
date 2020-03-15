package com.example.sakila.module.film;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.film.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
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

  public boolean filmExists(Long id) {
    return getFilmById(id) != null;
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

  public Film updateFilm(Long id, Film source) {
    Film target = filmRepository.getFilmById(id);
    if (target == null) throw new NotFoundException("Film for ID " + id + " does not exist");

    target.setTitle(source.getTitle());
    target.setDescription(source.getDescription());
    target.setReleaseYear(source.getReleaseYear());
    target.setLanguageId(source.getLanguageId());
    target.setOriginalLanguageId(source.getOriginalLanguageId());
    target.setRentalDuration(source.getRentalDuration());
    target.setRentalRate(source.getRentalRate());
    target.setLength(source.getLength());
    target.setReplacementCost(source.getReplacementCost());
    target.setRating(source.getRating());
    target.setCategoryId(source.getCategoryId());
    target.setSpecialFeatures(source.getSpecialFeatures());

    return filmRepository.updateFilm(target);
  }

  public void deleteFilm(Long id) {
    Film film = filmRepository.getFilmById(id);
    if (film == null) throw new NotFoundException("Film for ID " + id + " does not exist");

    try {
      filmRepository.deleteFilm(film);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
