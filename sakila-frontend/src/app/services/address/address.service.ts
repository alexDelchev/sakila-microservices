import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/address/services/api.service';
import { AddressDTO } from '@api/generated/address/models/address-dto';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor(private apiService: ApiService) { }

  getAddressById(id: number): Observable<AddressDTO> {
    return this.apiService.getAddressById(id);
  }

  getAddressesByCityId(id: number): Observable<Array<AddressDTO>> {
    return this.apiService.getAddressesByCityId(id);
  }

  getAddressesByCountryId(id: number): Observable<Array<AddressDTO>> {
    return this.apiService.getAddressesByCountryId(id);
  }
}
