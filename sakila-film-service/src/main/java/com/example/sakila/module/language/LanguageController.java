package com.example.sakila.module.language;

import com.example.sakila.generated.server.api.LanguagesApi;
import com.example.sakila.generated.server.model.LanguageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class LanguageController implements LanguagesApi {

  private final LanguageService languageService;

  @Autowired
  public LanguageController(LanguageService languageService) {
    this.languageService = languageService;
  }

  @Override
  public ResponseEntity<List<LanguageDTO>> getAllLanguages() {
    return ResponseEntity.ok(
        languageService.getAllLanguages().stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  private LanguageDTO toDTO(Language language) {
    LanguageDTO languageDTO = new LanguageDTO();
    languageDTO.setId(language.getId());
    languageDTO.setName(language.getName());
    languageDTO.setLastUpdate(OffsetDateTime.ofInstant(language.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return languageDTO;
  }
}
