import { Component } from '@angular/core';

import { ApiFilmCategory } from '@api/generated/film/models/api-film-category';

@Component({
  selector: 'app-category-picker',
  templateUrl: './category-picker.component.html',
  styleUrls: ['./category-picker.component.css']
})
export class CategoryPickerComponent {

  categories: Array<ApiFilmCategory> = ApiFilmCategory.values();

  constructor() { }

}
