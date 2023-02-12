import {Component, Input} from '@angular/core';

import {FilmSearchDTO} from '@api/generated/film/models/film-search-dto';
import {FilmSelectionService} from '../services/film-selection/film-selection.service';

@Component({
  selector: 'app-film',
  templateUrl: './film.component.html',
  styleUrls: ['./film.component.css']
})
export class FilmComponent {

  @Input() film: FilmSearchDTO;

  constructor(
    private filmSelectionService: FilmSelectionService
  ) {
  }

  isFilmSelected(): boolean {
    let selection: Array<string> = this.filmSelectionService.getSelectedFilmIds();
    if (selection == null) return false

    return selection.indexOf(this.film.id) > -1
  }

  doClick() {
    if (this.isFilmSelected()) {
      this.deselectFilm();
    } else {
      this.selectFilm();
    }
  }

  selectFilm() {
    this.filmSelectionService.addFilmToSelection(this.film.id);
  }

  deselectFilm() {
    this.filmSelectionService.removeFilmFromSelection(this.film.id);
  }
}
