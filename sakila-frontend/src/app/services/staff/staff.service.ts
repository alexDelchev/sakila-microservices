import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/store/services/api.service';
import { StaffDTO } from '@api/generated/store/models/staff-dto';

@Injectable({
  providedIn: 'root'
})
export class StaffService {

  constructor(private apiService: ApiService) { }

  getStaffById(id: number): Observable<StaffDTO> {
    return this.apiService.getStaffById(id);
  }

  getStaffByStoreId(id: number): Observable<Array<StaffDTO>> {
    return this.apiService.getStaffByStoreId(id);
  }

  createStaff(staff: StaffDTO): Observable<StaffDTO> {
    return this.apiService.createStaff(staff);
  }

  replaceStaff(id: number, staff: StaffDTO): Observable<StaffDTO> {
    return this.apiService.replaceStaff({ id: id, staffDTO: staff });
  }

  deleteStaff(id: number) {
    this.apiService.deleteStaff(id);
  }
}
