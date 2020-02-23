import { Component, OnInit } from '@angular/core';
import { FilmDTO } from '@api/generated/film/models/film-dto';
import { FilmService } from '../services/film/film.service';
import { FilmSelectionService } from '../services/film-selection/film-selection.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  private films: Array<FilmDTO>;

  constructor(
    private filmService: FilmService,
    private filmSelectionService: FilmSelectionService
  ) { }

  ngOnInit() {
    let filmIds: Array<number> = this.filmSelectionService.getSelectedFilmIds();
    getFilms(filmIds);
  }

  getTotalPrice(): number {
    let result: number = 0;

    for (let film of films) {
      result += film.getRentalRate();
    }

    return result;
  }

  private getFilms(filmIds: Array<number>) {
    for (let id: number of filmIds) {
      this.filmService.getFilmById(id).subscribe(result => this.films.push(result));
    }
  }
}
