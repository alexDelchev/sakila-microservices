import { Component, Input } from '@angular/core';

import { FilmService } from '../services/film/film.service';
import { FilmDTO } from '../api/generated/film/models/film-dto';
import { FilmSelectionService } from '../services/film-selection/film-selection.service';

@Component({
  selector: 'app-film',
  templateUrl: './film.component.html',
  styleUrls: ['./film.component.css']
})
export class FilmComponent {

  @Input() film: FilmDTO;

  constructor(private filmSelectionService: FilmSelectionService) {}

  isFilmSelected(): boolean {
    let selection: Array<number> = this.filmSelectionService.getSelectedFilmIds();
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
