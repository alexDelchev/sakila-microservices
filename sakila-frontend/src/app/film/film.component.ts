import { Component, Input } from '@angular/core';

import { FilmService } from '../services/film/film.service';
import { FilmDTO } from '../api/generated/film/models/film-dto';

@Component({
  selector: 'app-film',
  templateUrl: './film.component.html',
  styleUrls: ['./film.component.css']
})
export class FilmComponent {

  @Input() film: FilmDTO;

}
