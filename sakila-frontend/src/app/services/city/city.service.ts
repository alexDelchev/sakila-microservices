import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/address/services/api.service';
import { CityDTO } from '@api/generated/address/models/city-dto';

@Injectable({
  providedIn: 'root'
})
export class CityService {

  constructor(private apiService: ApiService) { }

  getAllCities(): Observable<Array<CityDTO>> {
    return this.apiService.getAllCities();
  }

  getCityById(id: number): Observable<CityDTO> {
    return this.apiService.getCityById(id);
  }

  getCitiesByCountryId(id: number): Observable<Array<CityDTO>> {
    return this.apiService.getCitiesByCountryId(id);
  }
}
