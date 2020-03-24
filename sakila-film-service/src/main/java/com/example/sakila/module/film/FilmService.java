package com.example.sakila.module.film;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.film.repository.FilmRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

  private final FilmRepository filmRepository;

  @Autowired
  public FilmService(FilmRepository filmRepository) {
    this.filmRepository = filmRepository;
  }

  public Film getFilmById(String hexString) {
    if (hexString == null) return null;
    ObjectId id = new ObjectId(hexString);
    return getFilmById(id);
  }

  public Film getFilmById(ObjectId id) {
    if (id == null) return null;
    return filmRepository.getFilmById(id);
  }

  public boolean filmExists(String hexString) {
    ObjectId id = new ObjectId(hexString);
    return filmExists(id);
  }

  public boolean filmExists(ObjectId id) {
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

  public List<Film> getFilmsByCategory(Category category) {
    if (category == null) return null;
    return filmRepository.getFilmsByCategory(category);
  }

  public List<Film> getFilmsByLanguage(Language language) {
    if (language == null) return null;
    return filmRepository.getFilmsByLanguage(language);
  }

  public List<Film> getFilmsByRating(String rating) {
    if (rating == null) return null;
    return filmRepository.getFilmsByRating(rating);
  }

  public Film createFilm(Film film) {
    return filmRepository.insertFilm(film);
  }

  public Film updateFilm(String hexString, Film source) {
    ObjectId id = new ObjectId(hexString);
    return updateFilm(id, source);
  }

  public Film updateFilm(ObjectId id, Film source) {
    Film target = filmRepository.getFilmById(id);
    if (target == null) throw new NotFoundException("Film for ID " + id + " does not exist");

    target.setTitle(source.getTitle());
    target.setDescription(source.getDescription());
    target.setReleaseYear(source.getReleaseYear());
    target.setLanguages(source.getLanguages());
    target.setOriginalLanguages(source.getOriginalLanguages());
    target.setRentalDuration(source.getRentalDuration());
    target.setRentalRate(source.getRentalRate());
    target.setLength(source.getLength());
    target.setReplacementCost(source.getReplacementCost());
    target.setRating(source.getRating());
    target.setCategories(source.getCategories());
    target.setSpecialFeatures(source.getSpecialFeatures());

    return filmRepository.updateFilm(target);
  }

  public void deleteFilm(String hexString) {
    if (hexString == null) return;
    ObjectId id = new ObjectId(hexString);
    deleteFilm(id);
  }

  public void deleteFilm(ObjectId id) {
    Film film = filmRepository.getFilmById(id);
    if (film == null) throw new NotFoundException("Film for ID " + id + " does not exist");

    try {
      filmRepository.deleteFilm(film);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
