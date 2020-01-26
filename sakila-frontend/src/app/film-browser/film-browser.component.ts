import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { FilmDTO } from '@api/generated/film/models/film-dto';
import { FilmService } from '../services/film/film.service';

@Component({
  selector: 'app-film-browser',
  templateUrl: './film-browser.component.html',
  styleUrls: ['./film-browser.component.css']
})
export class FilmBrowserComponent implements OnInit {

  private films: Array<FilmDTO>;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private filmService: FilmService
  ) { }

  ngOnInit() {
    let categoryId: number = this.getNumberParameterFromRoute('categoryId');
    if (categoryId) {
      this.filmService.getFilmsByCategoryId(categoryId).subscribe(result => this.films = result);
    }
  }

  private getNumberParameterFromRoute(name: string): number {
    return +this.route.snapshot.paramMap.get(name);
  }

}
