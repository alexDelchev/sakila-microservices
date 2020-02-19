import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { FilmDTO } from '@api/generated/film/models/film-dto';
import { FilmService } from '../services/film/film.service';
import { CategoryDTO } from '@api/generated/film/models/category-dto';
import { CategoryService } from '../services/category/category.service';
import { FilmRating } from '@api/generated/film/models/film-rating';
import { FilmSelectionService } from '../services/film-selection/film-selection.service';

@Component({
  selector: 'app-film-browser',
  templateUrl: './film-browser.component.html',
  styleUrls: ['./film-browser.component.css']
})
export class FilmBrowserComponent implements OnInit {

  private searchExpression: string;

  private category: CategoryDTO;

  private filmRating: FilmRating;

  private filmRatings: Array<FilmRating> = FilmRating.values();

  private categories: Array<CategoryDTO>;

  private films: Array<FilmDTO>;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private filmService: FilmService,
    private categoryService: CategoryService,
    private filmSelectionService: FilmSelectionService
  ) { }

  ngOnInit() {
    this.filmRating = FilmRating.PG_13
    this.categoryService.getAllCategories().subscribe(result => this.categories = result);

    let categoryId: number = this.getNumberParameterFromRoute('categoryId');
    if (categoryId) {
      this.categoryService.getCategoryById(categoryId).subscribe(
        result => {
          this.category = result;
          this.getFilms();
        }
      );
    }
  }

  changeCategorySelection() {
    this.getFilms();
  }

  changeFilmRatingSelection() {
    this.getFilms();
  }

  compareCategories(c1: CategoryDTO, c2: CategoryDTO): boolean {
    return c1.id === c2.id;
  }

  private getNumberParameterFromRoute(name: string): number {
    return +this.route.snapshot.paramMap.get(name);
  }

  private getFilms() {
    if (this.searchExpression != null) {
      this.filmService.searchFilmsByTitle(this.searchExpression).subscribe(
        result =>
          this.films = result.filter(film => film.categoryId === this.category.id && film.rating === this.filmRating)
      )
    } else {
      this.filmService.getFilmsByCategoryId(this.category.id).subscribe(
        result =>
          this.films = result.filter(film => film.rating === this.filmRating)
      )
    }
  }
}
