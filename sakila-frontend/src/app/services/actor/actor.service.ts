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

  replaceActor(id: string, actor: ActorDTO): Observable<ActorDTO> {
    return this.apiService.replaceActor({ id: id, actorDTO: actor });
  }

  deleteActor(id: string) {
    this.apiService.deleteActor(id)
  }

  getActorById(id: string): Observable<ActorDTO> {
    return this.apiService.getActorById(id);
  }
}
