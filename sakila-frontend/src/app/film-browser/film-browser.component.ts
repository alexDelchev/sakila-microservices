import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

import {FilmSearchDTO} from '@api/generated/film/models/film-search-dto';
import {ApiFilmCategory} from '@api/generated/film/models/api-film-category';
import {FilmService} from '../services/film/film.service';
import {FilmRating} from '@api/generated/film/models/film-rating';

@Component({
  selector: 'app-film-browser',
  templateUrl: './film-browser.component.html',
  styleUrls: ['./film-browser.component.css']
})
export class FilmBrowserComponent implements OnInit {

  searchExpression: string;

  category: ApiFilmCategory;

  filmRating: FilmRating;

  filmRatings: Array<FilmRating> = FilmRating.values();

  categories: Array<ApiFilmCategory> = ApiFilmCategory.values();

  films: Array<FilmSearchDTO>;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private filmService: FilmService
  ) {
  }

  ngOnInit() {
    this.filmRating = FilmRating.PG_13;

    let categoryString: string = this.getStringParameterFromRoute('category');
    this.category = categoryString as ApiFilmCategory;
    if (this.category) {
      this.getFilms();
    }
  }

  changeCategorySelection() {
    this.getFilms();
  }

  changeFilmRatingSelection() {
    this.getFilms();
  }

  private getStringParameterFromRoute(name: string): string {
    return this.route.snapshot.paramMap.get(name);
  }

  getFilms() {
    this.filmService.searchFilms(this.searchExpression, this.category, this.filmRating)
      .subscribe(result => this.films = result);
  }
}
