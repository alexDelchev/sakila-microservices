package com.example.sakila.module.film;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.model.FilmDTO;
import com.example.sakila.module.category.Category;
import com.example.sakila.module.category.CategoryService;
import com.example.sakila.module.language.Language;
import com.example.sakila.module.language.LanguageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

  @Mock
  private LanguageService languageService;

  @Mock
  private CategoryService categoryService;

  @InjectMocks
  private FilmController filmController;

  @Test
  void createFilm() {
    final long EXISTING_LANGUAGE_ID = 1;
    when(languageService.languageExists(EXISTING_LANGUAGE_ID)).thenReturn(true);

    final long NON_EXISTING_LANGUAGE_ID = -1L;
    when(languageService.languageExists(NON_EXISTING_LANGUAGE_ID)).thenReturn(false);

    final long NON_EXISTING_CATEGORY_ID = -1L;
    final List<Long> NON_EXISTING_CATEGORY_IDS = Collections.singletonList(NON_EXISTING_CATEGORY_ID);
    when(categoryService.categoryExists(NON_EXISTING_CATEGORY_ID)).thenReturn(false);

    FilmDTO invalidLanguageFilmDTO = new FilmDTO();
    invalidLanguageFilmDTO.setLanguageId(NON_EXISTING_LANGUAGE_ID);

    FilmDTO invalidCategoryFilmDTO = new FilmDTO();
    invalidCategoryFilmDTO.setLanguageId(EXISTING_LANGUAGE_ID);
    invalidCategoryFilmDTO.setCategoryIds(NON_EXISTING_CATEGORY_IDS);

    assertThrows(NotFoundException.class, () -> filmController.createFilm(invalidLanguageFilmDTO));
    assertThrows(NotFoundException.class, () -> filmController.createFilm(invalidCategoryFilmDTO));
  }

  @Test
  void replaceFilm() {
    final long EXISTING_LANGUAGE_ID = 1;
    when(languageService.languageExists(EXISTING_LANGUAGE_ID)).thenReturn(true);

    final long NON_EXISTING_LANGUAGE_ID = -1L;
    when(languageService.languageExists(NON_EXISTING_LANGUAGE_ID)).thenReturn(false);

    final long NON_EXISTING_CATEGORY_ID = -1L;
    final List<Long> NON_EXISTING_CATEGORY_IDS = Collections.singletonList(NON_EXISTING_CATEGORY_ID);
    when(categoryService.categoryExists(NON_EXISTING_CATEGORY_ID)).thenReturn(false);

    FilmDTO invalidLanguageFilmDTO = new FilmDTO();
    invalidLanguageFilmDTO.setLanguageId(NON_EXISTING_LANGUAGE_ID);

    FilmDTO invalidCategoryFilmDTO = new FilmDTO();
    invalidCategoryFilmDTO.setLanguageId(EXISTING_LANGUAGE_ID);
    invalidCategoryFilmDTO.setCategoryIds(NON_EXISTING_CATEGORY_IDS);

    assertThrows(NotFoundException.class, () -> filmController.replaceFilm(1L, invalidLanguageFilmDTO));
    assertThrows(NotFoundException.class, () -> filmController.replaceFilm(1L, invalidCategoryFilmDTO));
  }
}
