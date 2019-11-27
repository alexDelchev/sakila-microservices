package com.example.sakila.module.category;

import com.example.sakila.module.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category getCategoryById(Long id) {
    if (id == null) return null;
    return categoryRepository.getCategoryById(id);
  }

  public Category getCategoryByFilmId(Long filmId) {
    if (filmId == null) return null;
    return categoryRepository.getCategoryByFilmId(filmId);
  }

  public List<Category> getAllCategories() {
    return categoryRepository.getAllCategories();
  }
}
