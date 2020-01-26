import { Component, Input } from '@angular/core';

import { CategoryDTO } from '@api/generated/film/models/category-dto';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent {

  @Input() category: CategoryDTO;

}
