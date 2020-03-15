package com.example.sakila.module.film;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.FilmsApi;
import com.example.sakila.generated.server.model.FilmDTO;
import com.example.sakila.generated.server.model.FilmRating;
import com.example.sakila.module.category.Category;
import com.example.sakila.module.category.CategoryService;
import com.example.sakila.module.language.Language;
import com.example.sakila.module.language.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FilmController implements FilmsApi {

  private final FilmService filmService;

  private final CategoryService categoryService;

  private final LanguageService languageService;

  public FilmController(FilmService filmService, CategoryService categoryService, LanguageService languageService){
    this.filmService = filmService;
    this.categoryService = categoryService;
    this.languageService = languageService;
  }

  @Override
  public ResponseEntity<FilmDTO> getFilmById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(filmService.getFilmById(id)));
  }

  @Override
  public ResponseEntity<List<FilmDTO>> getFilmsByLanguageId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        filmService.getFilmsByLanguageId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<FilmDTO>> getFilmsByCategoryId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        filmService.getFilmsByCategoryId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<FilmDTO>> getFilmsByMpaaRating(@PathVariable("rating") String rating) {
    if (Arrays.stream(FilmRating.values()).noneMatch((x -> x.toString().equals(rating)))) return null;
    return ResponseEntity.ok(
        filmService.getFilmsByRating(rating).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<FilmDTO>> searchFilmsByTitle(
      @RequestParam(value = "expression", required = true) String expression
  ) {
    return ResponseEntity.ok(
        filmService.searchFilmsByTitle(expression).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<FilmDTO>> searchFilmsByDescription(
      @RequestParam(value = "expression", required = true) String expression
  ) {
    return ResponseEntity.ok(
        filmService.searchFilmsByDescription(expression).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<FilmDTO> createFilm(@RequestBody FilmDTO filmDTO) {
    return ResponseEntity.ok(toDTO(filmService.createFilm(toEntity(filmDTO))));
  }

  @Override
  public ResponseEntity<FilmDTO> replaceFilm(@PathVariable("id") Long id, @RequestBody FilmDTO filmDTO) {
    return ResponseEntity.ok(toDTO(filmService.updateFilm(id, toEntity(filmDTO))));
  }

  @Override
  public ResponseEntity<Void> deleteFilm(@PathVariable("id") Long id) {
    filmService.deleteFilm(id);
    return ResponseEntity.ok(null);
  }

  private FilmDTO toDTO(Film film) {
    return FilmUtils.toDTO(film);
  }

  private Film toEntity(FilmDTO filmDTO) {
    validateDTO(filmDTO);

    return FilmUtils.toEntity(filmDTO);
  }

  private void validateDTO(FilmDTO filmDTO) {
    if (filmDTO.getLanguageId() != null) checkLanguageExistence(filmDTO.getLanguageId());

    if (filmDTO.getOriginalLanguageId() != null) checkLanguageExistence(filmDTO.getOriginalLanguageId());

    if (filmDTO.getCategoryId() != null) checkCategoryExistence(filmDTO.getCategoryId());
  }

  private void checkLanguageExistence(Long id) {
    if (!languageService.languageExists(id)) throw new NotFoundException(
        "Language for ID " + id + " does not exist"
    );
  }

  private void checkCategoryExistence(Long id) {
    if (!categoryService.categoryExists(id)) throw new NotFoundException("Category for ID " + id + " does not exist");
  }
}
