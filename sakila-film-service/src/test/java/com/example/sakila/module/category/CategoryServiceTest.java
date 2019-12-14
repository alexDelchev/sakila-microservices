package com.example.sakila.module.category;

import com.example.sakila.module.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @InjectMocks
  private CategoryService categoryService;

  @Test
  void getCategoryById() {
    Category category = categoryService.getCategoryById(null);

    assertNull(category);
  }

  @Test
  void getCategoryByFilmId() {
    Category category = categoryService.getCategoryByFilmId(null);

    assertNull(category);
  }
}
