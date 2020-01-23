import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/store/services/api.service';
import { StaffDTO } from '@api/generated/store/models/staff-dto';

@Injectable({
  providedIn: 'root'
})
export class StaffService {

  constructor(private apiService: ApiService) { }

}
