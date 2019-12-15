package com.example.sakila.module.language.repository;

import com.example.sakila.module.language.Language;

import java.util.List;

public interface LanguageRepository {

  Language getLanguageById(Long id);

  Language getLanguageByFilmId(Long filmId);

  List<Language> getAllLanguages();

  Language insertLanguage(Language language);

  Language updateLanguage(Language language);

  void deleteLanguage(Language language);
}
