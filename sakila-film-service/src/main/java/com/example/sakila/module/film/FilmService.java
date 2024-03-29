package com.example.sakila.module.film;

import com.example.sakila.config.CachingService;
import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.model.ApiFilmCategory;
import com.example.sakila.generated.server.model.FilmDTO;
import com.example.sakila.generated.server.model.FilmRating;
import com.example.sakila.generated.server.model.FilmSearchDTO;
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService implements CachingService {

  private static final String CACHE_KEY = "film";

  private final Logger log = LoggerFactory.getLogger(FilmService.class);

  private final EventBus eventBus;

  private final FilmRepository filmRepository;

  private final FilmSearchService filmSearchService;

  @Autowired
  public FilmService(@Qualifier("FilmEventBus") EventBus eventBus, FilmRepository filmRepository,
                     FilmSearchService filmSearchService) {
    this.eventBus = eventBus;
    this.filmRepository = filmRepository;
    this.filmSearchService = filmSearchService;
  }

  @Cacheable(CACHE_KEY)
  public FilmDTO getFilmById(String hexString) {
    if (hexString == null || hexString.length() == 0) return null;
    ObjectId id = new ObjectId(hexString);
    return getFilmById(id);
  }

  @Cacheable(CACHE_KEY)
  public FilmDTO getFilmById(ObjectId id) {
    if (id == null) return null;
    return FilmUtils.toDTO(filmRepository.getFilmById(id));
  }

  @Cacheable(CACHE_KEY)
  public boolean filmExists(String hexString) {
    if (hexString == null || hexString.length() == 0) return false;
    ObjectId id = new ObjectId(hexString);
    return filmExists(id);
  }

  @Cacheable(CACHE_KEY)
  public boolean filmExists(ObjectId id) {
    return getFilmById(id) != null;
  }

  @Cacheable(CACHE_KEY)
  public List<FilmSearchDTO> searchFilms(String searchExpression, ApiFilmCategory category,
                                         FilmRating rating) {
    try {
      return filmSearchService.searchFilms(searchExpression, category, rating);
    } catch (IOException e) {
      log.error("Failed film search with parameters searchExperssion={}, category={}, rating={}",
          searchExpression, category, rating);
      throw new RuntimeException(e);
    }
  }

  @Cacheable(CACHE_KEY)
  public List<FilmSearchDTO> searchFilmsByTitle(String searchExpression) {
    if (searchExpression == null) return null;
    try {
      return filmSearchService.searchFilmsByTitle(searchExpression);
    } catch (IOException e) {
      log.error("Failed film search for title {}", searchExpression, e);
      throw new RuntimeException(e);
    }
  }

  @Cacheable(CACHE_KEY)
  public List<FilmSearchDTO> searchFilmsByDescription(String searchExpression) {
    if (searchExpression == null) return null;
    try {
      return filmSearchService.searchFilmsByDescription(searchExpression);
    } catch (IOException e) {
      log.error("Failed film search for description {}", searchExpression, e);
      throw new RuntimeException(e);
    }
  }

  @Cacheable(CACHE_KEY)
  public List<FilmDTO> getFilmsByCategory(Category category) {
    if (category == null) return null;
    return filmRepository.getFilmsByCategory(category)
        .stream()
        .map(FilmUtils::toDTO)
        .collect(Collectors.toList());
  }

  @Cacheable(CACHE_KEY)
  public List<FilmDTO> getFilmsByLanguage(Language language) {
    if (language == null) return null;
    return filmRepository.getFilmsByLanguage(language)
        .stream()
        .map(FilmUtils::toDTO)
        .collect(Collectors.toList());
  }

  @Cacheable(CACHE_KEY)
  public List<FilmDTO> getFilmsByRating(String rating) {
    if (rating == null) return null;
    return filmRepository.getFilmsByRating(rating)
        .stream()
        .map(FilmUtils::toDTO)
        .collect(Collectors.toList());
  }

  @CacheEvict(CACHE_KEY)
  public void decreaseQuantityForStore(String filmId, Long storeId) {
    Film film = filmRepository.getFilmById(new ObjectId(filmId));

    film.getInventories()
        .stream()
        .filter(i -> i.getStoreId().equals(storeId))
        .forEach(i -> {
          if (i.getQuantity() < 1) return;

          log.info("Decreasing quantity of Film (ID: {})", filmId);
          Integer newQuantity = i.getQuantity() - 1;
          i.setQuantity(newQuantity);
        });

    updateFilm(filmId, FilmUtils.toDTO(film));
  }

  @CacheEvict(CACHE_KEY)
  public FilmDTO createFilm(FilmDTO film) {
    FilmWriteModel writeModel = FilmUtils.toWriteModel(FilmUtils.toEntity(film));
    log.info("Creating Film");
    filmRepository.insertFilm(writeModel);
    log.info("Created Film id: {}", writeModel.getId());
    Film result = filmRepository.getFilmById(new ObjectId(film.getId()));

    generateCreatedEvent(result);

    return FilmUtils.toDTO(result);
  }

  private void generateCreatedEvent(Film film) {
    FilmEventDTO dto = FilmEventUtils.toDTO(film);
    FilmCreatedEvent event = new FilmCreatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  @CacheEvict(CACHE_KEY)
  public FilmDTO updateFilm(String hexString, FilmDTO source) {
    if (hexString == null || hexString.length() == 0) return null;
    ObjectId id = new ObjectId(hexString);
    return updateFilm(id, source);
  }

  public FilmDTO updateFilm(ObjectId id, FilmDTO sourceDto) {
    Film source = FilmUtils.toEntity(sourceDto);
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

    return FilmUtils.toDTO(result);
  }

  private void generateUpdatedEvent(Film film) {
    FilmEventDTO dto = FilmEventUtils.toDTO(film);
    FilmUpdatedEvent event = new FilmUpdatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  @CacheEvict(CACHE_KEY)
  public void deleteFilm(String hexString) {
    if (hexString == null || hexString.length() == 0) return;
    ObjectId id = new ObjectId(hexString);
    deleteFilm(id);
  }

  @CacheEvict(CACHE_KEY)
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

  private void generateDeletedEvent(String hexString) {
    FilmDeletedEvent event = new FilmDeletedEvent();
    event.setId(hexString);
    eventBus.emit(event);
  }

  @Override
  public String getCacheKey() {
    return CACHE_KEY;
  }
}
