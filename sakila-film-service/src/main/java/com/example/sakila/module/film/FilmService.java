package com.example.sakila.module.film;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.film.event.FilmEventUtils;
import com.example.sakila.module.film.event.model.FilmCreatedEvent;
import com.example.sakila.module.film.event.model.FilmDeletedEvent;
import com.example.sakila.module.film.event.model.FilmEventDTO;
import com.example.sakila.module.film.event.model.FilmUpdatedEvent;
import com.example.sakila.module.film.repository.FilmRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

  private final Logger log = LoggerFactory.getLogger(FilmService.class);

  private final EventBus eventBus;

  private final FilmRepository filmRepository;

  @Autowired
  public FilmService(@Qualifier("FilmEventBus") EventBus eventBus, FilmRepository filmRepository) {
    this.eventBus = eventBus;
    this.filmRepository = filmRepository;
  }

  public Film getFilmById(String hexString) {
    if (hexString == null || hexString.length() == 0) return null;
    ObjectId id = new ObjectId(hexString);
    return getFilmById(id);
  }

  public Film getFilmById(ObjectId id) {
    if (id == null) return null;
    return filmRepository.getFilmById(id);
  }

  public boolean filmExists(String hexString) {
    if (hexString == null || hexString.length() == 0) return false;
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

  public void decreaseQuantityForStore(String filmId, Long storeId) {
    Film film = getFilmById(filmId);

    film.getInventories()
        .stream()
        .filter(i -> i.getStoreId().equals(storeId))
        .forEach(i -> {
          if (i.getQuantity() < 1) return;

          log.info("Decreasing quantity of Film (ID: {})", filmId);
          Integer newQuantity = i.getQuantity() -1;
          i.setQuantity(newQuantity);
        });

    updateFilm(filmId, film);
  }

  private void generateCreatedEvent(Film film) {
    FilmEventDTO dto = FilmEventUtils.toDTO(film);
    FilmCreatedEvent event = new FilmCreatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  public Film createFilm(Film film) {
    FilmWriteModel writeModel = FilmUtils.toWriteModel(film);
    log.info("Creating Film");
    filmRepository.insertFilm(writeModel);
    log.info("Created Film id: {}", writeModel.getId());
    Film result = filmRepository.getFilmById(film.getId());

    generateCreatedEvent(result);

    return result;
  }

  private void generateUpdatedEvent(Film film) {
    FilmEventDTO dto = FilmEventUtils.toDTO(film);
    FilmUpdatedEvent event = new FilmUpdatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  public Film updateFilm(String hexString, Film source) {
    if (hexString == null || hexString.length() == 0) return null;
    ObjectId id = new ObjectId(hexString);
    return updateFilm(id, source);
  }

  public Film updateFilm(ObjectId id, Film source) {
    Film target = filmRepository.getFilmById(id);
    if (target == null) throw new NotFoundException("Film for ID " + id + " does not exist");
    log.info("Updating Film (ID: {})", id.toHexString());

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
    target.setActors(source.getActors());
    target.setInventories(source.getInventories());

    FilmWriteModel writeModel = FilmUtils.toWriteModel(target);

    filmRepository.updateFilm(writeModel);

    Film result = filmRepository.getFilmById(id);

    generateUpdatedEvent(result);

    return result;
  }

  private void generateDeletedEvent(String hexString) {
    FilmDeletedEvent event = new FilmDeletedEvent();
    event.setId(hexString);
    eventBus.emit(event);
  }

  public void deleteFilm(String hexString) {
    if (hexString == null || hexString.length() == 0) return;
    ObjectId id = new ObjectId(hexString);
    deleteFilm(id);
  }

  public void deleteFilm(ObjectId id) {
    Film film = filmRepository.getFilmById(id);
    if (film == null) throw new NotFoundException("Film for ID " + id + " does not exist");
    log.info("Deleting Film (ID: {})", id.toHexString());

    try {
      filmRepository.deleteFilm(film);
      generateDeletedEvent(id.toHexString());
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
