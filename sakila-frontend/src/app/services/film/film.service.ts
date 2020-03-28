import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/film/services/api.service';
import { FilmDTO } from '@api/generated/film/models/film-dto';
import { ApiFilmCategory } from '@api/generated/film/models/api-film-category';
import { ApiFilmLanguage } from '@api/generated/film/models/api-film-language';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  constructor(private apiService: ApiService) { }

  createFilm(film: FilmDTO): Observable<FilmDTO> {
    return this.apiService.createFilm(film);
  }

  replaceFilm(id: string, film: FilmDTO): Observable<FilmDTO> {
    return this.apiService.replaceFilm({ id: id, FilmDTO: film });
  }

  deleteFilm(id: string) {
    this.apiService.deleteFilm(id);
  }

  getFilmById(id: string): Observable<FilmDTO> {
    return this.apiService.getFilmById(id);
  }

  searchFilmsByTitle(expression: string): Observable<Array<FilmDTO>> {
    return this.apiService.searchFilmsByTitle(expression);
  }

  searchFilmsByDescription(expression: string): Observable<Array<FilmDTO>> {
    return this.apiService.searchFilmsByDescription(expression);
  }

  getFilmsByCategory(category: ApiFilmCategory): Observable<Array<FilmDTO>> {
    return this.apiService.getFilmsByCategory(category);
  }

  getFilmsByLanguageId(language: ApiFilmLanguage): Observable<Array<FilmDTO>> {
    return this.apiService.getFilmsByLanguage(language);
  }

  getFilmsByMpaaRating(rating: string): Observable<Array<FilmDTO>> {
    return this.apiService.getFilmsByMpaaRating(rating);
  }
}
