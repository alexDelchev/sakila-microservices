import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/payment/services/api.service';
import { PaymentDTO } from '@api/generated/payment/models/payment-dto';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  constructor(private apiService: ApiService) { }

  getPaymentById(id: number): Observable<PaymentDTO> {
    return this.apiService.getPaymentById(id);
  }

  getPaymentsByRentalId(id: number): Observable<Array<PaymentDTO>> {
    return this.apiService.getPaymentsByRentalId(id);
  }

  getPaymentsByCustomerId(id: number): Observable<Array<PaymentDTO>> {
    return this.apiService.getPaymentsByCustomerId(id);
  }

  getPaymentsByStaffId(id: number): Observable<Array<PaymentDTO>> {
    return this.apiService.getPaymentsByStaffId(id);
  }

  createPayment(payment: PaymentDTO): Observable<PaymentDTO> {
    return this.apiService.createPayment(payment);
  }

  replacePayment(id: number, payment: PaymentDTO): Observable<PaymentDTO> {
    return this.apiService.replacePayment({ id: id, paymentDTO: payment });
  }

  deletePayment(id: number) {
    this.apiService.deletePayment(id);
  }
}
