import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { FilmDTO } from '@api/generated/film/models/film-dto';
import { ApiFilmCategory } from '@api/generated/film/models/api-film-category';
import { FilmService } from '../services/film/film.service';
import { FilmRating } from '@api/generated/film/models/film-rating';
import { FilmSelectionService } from '../services/film-selection/film-selection.service';

@Component({
  selector: 'app-film-browser',
  templateUrl: './film-browser.component.html',
  styleUrls: ['./film-browser.component.css']
})
export class FilmBrowserComponent implements OnInit {

  private searchExpression: string;

  private category: ApiFilmCategory;

  private filmRating: FilmRating;

  private filmRatings: Array<FilmRating> = FilmRating.values();

  private categories: Array<ApiFilmCategory> = ApiFilmCategory.values();

  private films: Array<FilmDTO>;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private filmService: FilmService,
    private filmSelectionService: FilmSelectionService
  ) { }

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

  private getFilms() {
    if (this.searchExpression != null) {
      this.filmService.searchFilmsByTitle(this.searchExpression).subscribe(
        result =>
          this.films = result.filter(film => film.categories.indexOf(this.category) > -1 && film.rating === this.filmRating)
      )
    } else {
      this.filmService.getFilmsByCategory(this.category).subscribe(
        result =>
          this.films = result.filter(film => film.rating === this.filmRating)
      )
    }
  }
}
