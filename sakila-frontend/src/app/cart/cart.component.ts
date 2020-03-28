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
    let filmIds: Array<string> = this.filmSelectionService.getSelectedFilmIds();
    this.films = [];
    this.getFilms(filmIds);
  }

  getTotalPrice(): number {
    let result: number = 0;

    for (let film of this.films) {
      result += film.rentalRate;
    }

    return result;
  }

  private getFilms(filmIds: Array<string>) {
    for (let id of filmIds) {
      this.filmService.getFilmById(id).subscribe(result => this.films.push(result));
    }
  }
}
