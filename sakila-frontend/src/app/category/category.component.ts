import { Component, Input } from '@angular/core';

import { ApiFilmCategory } from '@api/generated/film/models/api-film-category';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent {

  @Input() category: ApiFilmCategory;

}
