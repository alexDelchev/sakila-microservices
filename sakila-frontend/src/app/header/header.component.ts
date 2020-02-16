import { Component, OnInit } from '@angular/core';

import { FilmSelectionService } from '../services/film-selection/film-selection.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private filmSelectionService: FilmSelectionService) { }

  ngOnInit() {
  }

  getFilmSelectionCount(): number {
    let selection: Array<number> = this.filmSelectionService.getSelectedFilmIds();

    if (selection === null) return 0;

    return selection.length;
  }

}
