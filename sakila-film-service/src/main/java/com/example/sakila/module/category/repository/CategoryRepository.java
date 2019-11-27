package com.example.sakila.module.category.repository;

import com.example.sakila.module.category.Category;

import java.util.List;

public interface CategoryRepository {

  Category getCategoryById(Long id);

  Category getCategoryByFilmId(Long filmId);

  List<Category> getAllCategories();
}
