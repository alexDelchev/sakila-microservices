import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/address/services/api.service';
import { AddressDTO } from '@api/generated/address/models/address-dto';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor(private apiService: ApiService) { }

  createAddress(address: AddressDTO): Observable<AddressDTO> {
    return this.apiService.createAddress(address);
  }

  replaceAddress(id: number, address: AddressDTO): Observable<AddressDTO> {
    return this.apiService.replaceAddress({ id: id, addressDTO: address });
  }

  deleteAddress(id: number) {
    this.apiService.deleteAddress(id);
  }

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
