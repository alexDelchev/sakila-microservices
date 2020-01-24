import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/address/services/api.service';
import { CountryDTO } from '@api/generated/address/models/country-dto';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  constructor(private apiService: ApiService) { }

  getAllCountries(): Observable<Array<CountryDTO>> {
    return this.apiService.getAllCountries();
  }

  getCountryById(id: number): Observable<CountryDTO> {
    return this.apiService.getCountryById(id);
  }

  createCountry(country: CountryDTO): Observable<CountryDTO> {
    return this.apiService.createCountry(country);
  }

  replaceCountry(id: number, country: CountryDTO): Observable<CountryDTO> {
    return this.apiService.replaceCountry({ id: id, CountryDTO: country });
  }

  deleteCountry(id: number) {
    this.apiService.deleteCountry(id);
  }
}
