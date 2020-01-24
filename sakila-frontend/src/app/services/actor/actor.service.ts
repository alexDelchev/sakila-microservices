import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/film/services/api.service';
import { ActorDTO } from '@api/generated/film/models/actor-dto';

@Injectable({
  providedIn: 'root'
})
export class ActorService {

  constructor(private apiService: ApiService) { }

  createActor(actor: ActorDTO): Observable<ActorDTO> {
    return this.apiService.createActor(actor);
  }

  replaceActor(id: number, actor: ActorDTO): Observable<ActorDTO> {
    return this.apiService.replaceActor({ id: id, actorDTO: actor });
  }

  deleteActor(id: number) {
    this.apiService.deleteActor(id);
  }

  getActorById(id: number): Observable<Actor> {
    return this.apiService.getActorById(id);
  }

  getActorsByFilmId(id: number): Observable<Array<Actor>> {
    return this.apiService.getActorsByFilmId(id);
  }
}
