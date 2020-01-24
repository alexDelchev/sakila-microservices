import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/payment/services/api.service';
import { CustomerDTO } from '@api/generated/payment/models/customer-dto';

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private apiService: ApiService) { }

  getCustomerById(id: number): Observable<CustomerDTO> {
    return this.apiService.getCustomerById(id);
  }

  getCustomersByStoreId(id: number): Observable<Array<CustomerDTO>> {
    return this.apiService.getCustomersByStoreId(id);
  }

  searchCustomersByFirstName(expression: string): Observable<Array<CustomerDTO>> {
    return this.apiService.searchCustomersByFirstName(expression);
  }

  searchCustomersByLastName(expression: string): Observable<Array<CustomerDTO>> {
    return this.apiService.searchCustomersByLastName(expression);
  }
}
