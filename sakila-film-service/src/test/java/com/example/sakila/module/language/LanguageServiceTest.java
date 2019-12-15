package com.example.sakila.module.language;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.language.repository.LanguageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {

  @Mock
  private LanguageRepository languageRepository;

  @InjectMocks
  private LanguageService languageService;

  @Test
  void getLanguageById() {
    Language language = languageService.getLanguageById(null);

    assertNull(language);
  }

  @Test
  void getLanguageByFilmId() {
    Language language = languageService.getLanguageByFilmId(null);

    assertNull(language);
  }

  @Test
  void updateLanguage() {
    final long EXISTING_LANGUAGE_ID = 1L;
    when(languageRepository.getLanguageById(EXISTING_LANGUAGE_ID)).thenReturn(new Language());

    final long NON_EXISTING_LANGUAGE_ID = -1L;
    when(languageRepository.getLanguageById(NON_EXISTING_LANGUAGE_ID)).thenReturn(null);

    when(languageRepository.updateLanguage(any(Language.class))).thenReturn(new Language());

    assertDoesNotThrow(() -> languageService.updateLanguage(EXISTING_LANGUAGE_ID, new Language()));
    assertThrows(
        NotFoundException.class,
        () -> languageService.updateLanguage(NON_EXISTING_LANGUAGE_ID, new Language())
    );
  }

  @Test
  void deleteLanguage() {
    final long NON_EXISTING_LANGUAGE_ID = -1L;

    assertThrows(NotFoundException.class, () -> languageService.deleteLanguage(NON_EXISTING_LANGUAGE_ID));
  }
}
