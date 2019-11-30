package com.example.sakila.module.language;

import com.example.sakila.module.language.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

  private final LanguageRepository languageRepository;

  @Autowired
  public LanguageService(LanguageRepository languageRepository) {
    this.languageRepository = languageRepository;
  }

  public Language getLanguageById(Long id) {
    if (id == null) return null;
    return languageRepository.getLanguageById(id);
  }

  public Language getLanguageByFilmId(Long filmId) {
    if (filmId == null) return null;
    return languageRepository.getLanguageByFilmId(filmId);
  }

  public List<Language> getAllLanguages() {
    return languageRepository.getAllLanguages();
  }
}