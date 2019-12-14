package com.example.sakila.module.category;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

  @Test
  void updateCategory() {
    final long EXISTING_CATEGORY_ID = 1L;
    when(categoryRepository.getCategoryById(EXISTING_CATEGORY_ID)).thenReturn(new Category());

    final long NON_EXISTING_CATEGORY_ID = -1L;
    when(categoryRepository.getCategoryById(NON_EXISTING_CATEGORY_ID)).thenReturn(null);

    when(categoryRepository.updateCategory(any(Category.class))).thenReturn(new Category());

    assertDoesNotThrow(() -> categoryService.updateCategory(EXISTING_CATEGORY_ID, new Category()));
    assertThrows(
        NotFoundException.class,
        () -> categoryService.updateCategory(NON_EXISTING_CATEGORY_ID, new Category())
    );
  }

  @Test
  void deleteCategory() {
    final long NON_EXISTING_CATEGORY_ID = -1L;

    assertThrows(NotFoundException.class, () -> categoryService.deleteCategory(NON_EXISTING_CATEGORY_ID));
  }
}
