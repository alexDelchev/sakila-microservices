package com.example.sakila.module.language;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.language.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

  public boolean languageExists(Long id) {
    return getLanguageById(id) != null;
  }

  public Language getLanguageByFilmId(Long filmId) {
    if (filmId == null) return null;
    return languageRepository.getLanguageByFilmId(filmId);
  }

  public Language createLanguage(Language language) {
    return languageRepository.insertLanguage(language);
  }

  public Language updateLanguage(Long id, Language source) {
    Language target = languageRepository.getLanguageById(id);
    if (target == null) throw new NotFoundException("Language for ID " + id + " does not exist");

    target.setName(source.getName());

    return languageRepository.updateLanguage(target);
  }

  public void deleteLanguage(Long id) {
    Language language = languageRepository.getLanguageById(id);
    if (language == null) throw new NotFoundException("Language for ID " + id + " does not exist");

    try {
      languageRepository.deleteLanguage(language);
    } catch(DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }

  public List<Language> getAllLanguages() {
    return languageRepository.getAllLanguages();
  }
}
