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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

  @Mock
  private FilmService filmService;

  @Mock
  private LanguageService languageService;

  @Mock
  private CategoryService categoryService;

  @InjectMocks
  private FilmController filmController;

  @Test
  void createFilm() {
    final long EXISTING_LANGUAGE_ID = 1;
    when(languageService.getLanguageById(EXISTING_LANGUAGE_ID)).thenReturn(new Language());

    final long NON_EXISTING_LANGUAGE_ID = -1L;
    when(languageService.getLanguageById(NON_EXISTING_LANGUAGE_ID)).thenReturn(null);

    final long NON_EXISTING_CATEGORY_ID = -1L;
    when(categoryService.getCategoryById(NON_EXISTING_CATEGORY_ID)).thenReturn(null);

    FilmDTO invalidLanguageFilmDTO = new FilmDTO();
    invalidLanguageFilmDTO.setLanguageId(NON_EXISTING_LANGUAGE_ID);

    FilmDTO invalidCategoryFilmDTO = new FilmDTO();
    invalidCategoryFilmDTO.setLanguageId(EXISTING_LANGUAGE_ID);
    invalidCategoryFilmDTO.setCategoryId(NON_EXISTING_CATEGORY_ID);

    assertThrows(NotFoundException.class, () -> filmController.createFilm(invalidLanguageFilmDTO));
    assertThrows(NotFoundException.class, () -> filmController.createFilm(invalidCategoryFilmDTO));
  }
}
