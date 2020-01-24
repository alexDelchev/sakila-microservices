import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/store/services/api.service';
import { StoreDTO } from '@api/generated/store/models/store-dto';

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  constructor(private apiService: ApiService) { }

  getStoreById(id: number): Observable<StoreDTO> {
    return this.apiService.getStoreById(id);
  }

  getStoreByAddressId(id: number): Observable<StoreDTO> {
    return this.apiService.getStoreByAddressId(id);
  }
}
