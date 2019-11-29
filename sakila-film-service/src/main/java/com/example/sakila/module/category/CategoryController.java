package com.example.sakila.module.category;

import com.example.sakila.generated.server.api.CategoriesApi;
import com.example.sakila.generated.server.model.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryController implements CategoriesApi {

  private final CategoryService categoryService;

  @Autowired
  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Override
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    return ResponseEntity.ok(
        categoryService.getAllCategories().stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(categoryService.getCategoryById(id)));
  }

  private CategoryDTO toDTO(Category category) {
    CategoryDTO categoryDTO = new CategoryDTO();
    categoryDTO.setId(category.getId());
    categoryDTO.setName(category.getName());
    categoryDTO.setLastUpdate(OffsetDateTime.ofInstant(category.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return categoryDTO;
  }

}
