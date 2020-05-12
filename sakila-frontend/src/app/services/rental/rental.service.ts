import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/payment/services/api.service';
import { RentalDTO } from '@api/generated/payment/models/rental-dto';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  constructor(private apiService: ApiService) { }

  getRentalById(id: number): Observable<RentalDTO> {
    return this.apiService.getRentalById(id);
  }

  getRentalsByCustomerId(id: number): Observable<Array<RentalDTO>> {
    return this.apiService.getRentalsByCustomerId(id);
  }

  getRentalsByStaffId(id: number): Observable<Array<RentalDTO>> {
    return this.apiService.getRentalsByStaffId(id);
  }

  createRental(rental: RentalDTO): Observable<RentalDTO> {
    return this.apiService.createRental(rental);
  }

  replaceRental(id: number, rental: RentalDTO): Observable<RentalDTO> {
    return this.apiService.replaceRental({ id: id, rentalDTO: rental });
  }

  deleteRental(id: number) {
    this.apiService.deleteRental(id);
  }
}
