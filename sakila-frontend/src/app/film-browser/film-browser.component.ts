import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { FilmDTO } from '@api/generated/film/models/film-dto';
import { FilmService } from '../services/film/film.service';
import { CategoryDTO } from '@api/generated/film/models/category-dto';
import { CategoryService } from '../services/category/category.service';

@Component({
  selector: 'app-film-browser',
  templateUrl: './film-browser.component.html',
  styleUrls: ['./film-browser.component.css']
})
export class FilmBrowserComponent implements OnInit {

  private category: CategoryDTO;

  private categories: Array<CategoryDTO>;

  private films: Array<FilmDTO>;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private filmService: FilmService,
    private categoryService: CategoryService
  ) { }

  ngOnInit() {
    this.categoryService.getAllCategories().subscribe(result => this.categories = result);

    let categoryId: number = this.getNumberParameterFromRoute('categoryId');
    if (categoryId) {
      this.filmService.getFilmsByCategoryId(categoryId).subscribe(result => this.films = result);
    }
  }

  changeCategorySelection() {
    this.filmService.getFilmsByCategoryId(this.category.id).subscribe(result => this.films = result);
  }

  private getNumberParameterFromRoute(name: string): number {
    return +this.route.snapshot.paramMap.get(name);
  }

}
