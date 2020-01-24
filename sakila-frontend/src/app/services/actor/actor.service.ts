import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/film/services/api.service';
import { ActorDTO } from '@api/generated/film/models/actor-dto';

@Injectable({
  providedIn: 'root'
})
export class ActorService {

  constructor(private apiService: ApiService) { }

  getActorById(id: number): Observable<Actor> {
    return this.apiService.getActorById(id);
  }

  getActorsByFilmId(id: number): Observable<Array<Actor>> {
    return this.apiService.getActorsByFilmId(id);
  }
}
