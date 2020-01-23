import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/film/services/api.service';
import { FilmDTO } from '@api/generated/film/models/film-dto';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  constructor(private apiService: ApiService) { }

  getFilmById(id: number): Observable<FilmDTO> {
    return this.apiService.getFilmById(id);
  }
}
