package com.example.sakila.module.language;

import com.example.sakila.module.language.repository.LanguageRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {

  @Mock
  private LanguageRepository languageRepository;

  @InjectMocks
  private LanguageService languageService;
  
}
