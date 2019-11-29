package com.example.sakila.module.language;

import com.example.sakila.module.language.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LanguageController {

  private final LanguageService languageService;

  @Autowired
  public LanguageController(LanguageService languageService) {
    this.languageService = languageService;
  }
}
