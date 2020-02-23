import { Component, OnInit } from '@angular/core';
import { FilmService } from '../services/film/film.service';
import { FilmSelectionService } from '../services/film-selection/film-selection.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  constructor(
    private filmService: FilmService,
    private filmSelectionService: FilmSelectionService
  ) { }

  ngOnInit() {
  }

}
