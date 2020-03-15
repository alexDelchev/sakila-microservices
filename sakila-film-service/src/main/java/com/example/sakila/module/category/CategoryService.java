package com.example.sakila.module.category;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

  public boolean categoryExists(Long id) {
    return getCategoryById(id) != null;
  }

  public Category getCategoryByFilmId(Long filmId) {
    if (filmId == null) return null;
    return categoryRepository.getCategoryByFilmId(filmId);
  }

  public Category createCategory(Category category) {
    return categoryRepository.insertCategory(category);
  }

  public Category updateCategory(Long id, Category source) {
    Category target = categoryRepository.getCategoryById(id);
    if (target == null) throw new NotFoundException("Category for ID " + id + " does not exist");

    target.setName(source.getName());

    return categoryRepository.updateCategory(target);
  }

  public void deleteCategory(Long id) {
    Category category = categoryRepository.getCategoryById(id);
    if (category == null) throw new NotFoundException("Category for ID " + id + " does not exist");

    try {
      categoryRepository.deleteCategory(category);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }

  public List<Category> getAllCategories() {
    return categoryRepository.getAllCategories();
  }
}
