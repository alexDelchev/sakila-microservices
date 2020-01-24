import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/film/services/api.service';
import { FilmDTO } from '@api/generated/film/models/film-dto';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  constructor(private apiService: ApiService) { }

  createFilm(film: FilmDTO): Observable<FilmDTO> {
    return this.apiService.createFilm(film);
  }

  replaceFilm(id: number, film: FilmDTO): Observable<FilmDTO> {
    return this.apiService.replaceFilm({ id: id, FilmDTO: film });
  }

  deleteFilm(id: number) {
    this.apiService.deleteFilm(id);
  }

  getFilmById(id: number): Observable<FilmDTO> {
    return this.apiService.getFilmById(id);
  }

  searchFilmsByTitle(expression: string): Observable<Array<FilmDTO>> {
    return this.apiService.searchFilmsByTitle(expression);
  }

  searchFilmsByDescription(expression: string): Observable<Array<FilmDTO>> {
    return this.apiService.searchFilmsByDescription(expression);
  }

  getFilmsByCategoryId(id: number): Observable<Array<FilmDTO>> {
    return this.apiService.getFilmsByCategoryId(id);
  }

  getFilmsByLanguageId(id: number): Observable<Array<FilmDTO>> {
    return this.apiService.getFilmsByLanguageId(id);
  }

  getFilmsByMpaaRating(rating: string): Observable<Array<FilmDTO>> {
    return this.apiService.getFilmsByMpaaRating(rating);
  }
}
