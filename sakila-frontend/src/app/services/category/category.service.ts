import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/film/services/api.service';
import { CategoryDTO } from '@api/generated/film/models/category-dto';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private apiService: ApiService) { }

  getAllCategories(): Observable<Array<CategoryDTO>> {
    return this.apiService.getAllCategories();
  }

  getCategoryById(id: number): Observable<CategoryDTO> {
    return this.apiService.getCategoryById(id);
  }

  createCategory(category: CategoryDTO): Observable<CategoryDTO> {
    return this.apiService.createCategory(category);
  }

  replaceCategory(id: number, category: CategoryDTO): Observable<CategoryDTO> {
    return this.apiService.replaceCategory({ id: id, CategoryDTO: category });
  }

  deleteCategory(id: number) {
    this.apiService.deleteCategory(id);
  }
}
