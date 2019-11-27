package com.example.sakila.module.category;

import com.example.sakila.generated.server.api.CategoriesApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CategoryController implements CategoriesApi {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }
}
