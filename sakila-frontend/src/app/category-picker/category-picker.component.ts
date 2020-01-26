import { Component, OnInit } from '@angular/core';

import { CategoryService } from '../services/category/category.service';
import { CategoryDTO } from '@api/generated/film/models/category-dto';

@Component({
  selector: 'app-category-picker',
  templateUrl: './category-picker.component.html',
  styleUrls: ['./category-picker.component.css']
})
export class CategoryPickerComponent implements OnInit {

  categories: Array<CategoryDTO>

  constructor(private categoryService: CategoryService) { }

  ngOnInit() {
    this.categoryService.getAllCategories().subscribe(result => this.categories = result);
  }

}
