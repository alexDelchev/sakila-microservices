package com.example.sakila.module.film;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.FilmsApi;
import com.example.sakila.generated.server.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FilmController implements FilmsApi {

  private final FilmService filmService;

  public FilmController(FilmService filmService) {
    this.filmService = filmService;
  }

  @Override
  public ResponseEntity<FilmDTO> getFilmById(@PathVariable("id") String id) {
    return ResponseEntity.ok(filmService.getFilmById(id));
  }

  @Override
  public ResponseEntity<List<FilmDTO>> getFilmsByLanguage(@PathVariable("language") String languageName) {
    Language language = Language.valueOf(languageName);
    return ResponseEntity.ok(filmService.getFilmsByLanguage(language));
  }

  @Override
  public ResponseEntity<List<FilmSearchDTO>> getFilmsByCategory(@PathVariable("category") String categoryName) {
    Category category = Category.valueOf(categoryName);
    return ResponseEntity.ok(filmService.getFilmsByCategory(category).stream()
        .map(FilmUtils::toSearchDto)
        .collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<List<FilmDTO>> getFilmsByMpaaRating(@PathVariable("rating") String rating) {
    if (Arrays.stream(FilmRating.values()).noneMatch((x -> x.toString().equals(rating)))) return null;
    return ResponseEntity.ok(filmService.getFilmsByRating(rating));
  }

  @Override
  public ResponseEntity<List<FilmSearchDTO>> searchFilms(
      @RequestParam(value = "expression", required = false) String searchExpression,
      @RequestParam(value = "category", required = false) String categoryExpression,
      @RequestParam(value = "rating", required = false) String ratingExpression) {

    ApiFilmCategory category = StringUtils.hasText(categoryExpression) ?
        ApiFilmCategory.fromValue(categoryExpression) : null;
    FilmRating rating = StringUtils.hasText(ratingExpression) ?
        FilmRating.fromValue(ratingExpression) : null;

    return ResponseEntity.ok(filmService.searchFilms(searchExpression, category, rating));
  }

  @Override
  public ResponseEntity<FilmDTO> createFilm(@RequestBody FilmDTO filmDTO) {
    return ResponseEntity.ok(filmService.createFilm(filmDTO));
  }

  @Override
  public ResponseEntity<FilmDTO> replaceFilm(@PathVariable("id") String id, @RequestBody FilmDTO filmDTO) {
    validateDTO(filmDTO);
    return ResponseEntity.ok(filmService.updateFilm(id, filmDTO));
  }

  @Override
  public ResponseEntity<Void> deleteFilm(@PathVariable("id") String id) {
    filmService.deleteFilm(id);
    return ResponseEntity.ok(null);
  }

  private void validateDTO(FilmDTO filmDTO) {
    if (filmDTO.getLanguages() != null) {
      filmDTO.getLanguages()
          .stream()
          .map(ApiFilmLanguage::toString)
          .forEach(this::checkLanguageExistence);
    }

    if (filmDTO.getOriginalLanguages() != null) {
      filmDTO.getLanguages()
          .stream()
          .map(ApiFilmLanguage::toString)
          .forEach(this::checkLanguageExistence);
    }

    if (filmDTO.getCategories() != null) {
      filmDTO.getCategories()
          .stream()
          .map(ApiFilmCategory::toString)
          .forEach(this::checkCategoryExistence);
    }
  }

  private void checkLanguageExistence(String languageName) {
    boolean exists = false;

    for (Language language : Language.values()) {
      if (language.toString().equals(languageName)) {
        exists = true;
        break;
      }
    }

    if (!exists) throw new NotFoundException(String.format("Language for name %s does not exist", languageName));
  }

  private void checkCategoryExistence(String categoryName) {
    boolean exists = false;

    for (Category category : Category.values()) {
      if (category.toString().equals(categoryName)) {
        exists = true;
        break;
      }
    }

    if (!exists) throw new NotFoundException(String.format("Category for name %s does not exist", categoryName));
  }
}
