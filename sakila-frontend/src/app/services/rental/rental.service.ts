import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/payment/services/api.service';
import { RentalDTO } from '@api/generated/payment/models/rental-dto';

@Injectable({
  providedIn: 'root'
})
export class RentalService {

  constructor(private apiService: ApiService) { }

}
