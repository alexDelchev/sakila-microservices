import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService as ReadApiService } from '@api/generated/store/read/services/api.service';
import { ApiService as WriteApiService } from '@api/generated/store/write/services/api.service';
import { StaffDTO } from '@api/generated/store/read/models/staff-dto';
import { CreateStaffCommandDTO } from '@api/generated/store/write/models/create-staff-command-dto';
import { DeleteStaffCommandDTO } from '@api/generated/store/write/models/delete-staff-command-dto';
import { BasicBooleanCommandDTO } from '@api/generated/store/write/models/basic-boolean-command-dto';
import { BasicInt64CommandDTO } from '@api/generated/store/write/models/basic-int-64command-dto';
import { BasicStringCommandDTO } from '@api/generated/store/write/models/basic-string-command-dto';
import { AggregateIdDTO } from '@api/generated/store/write/models/aggregate-id-dto';

@Injectable({
  providedIn: 'root'
})
export class StaffService {

  constructor(private readApiService: ReadApiService, private writeApiService: WriteApiService) { }

  getStaffById(id: number): Observable<StaffDTO> {
    return this.readApiService.getStaffById(id);
  }

  getStaffByStoreId(id: number): Observable<Array<StaffDTO>> {
    return this.readApiService.getStaffByStoreId(id);
  }

  createStaff(command: CreateStaffCommandDTO): Observable<AggregateIdDTO> {
    return this.writeApiService.createStaff(command);
  }

  changeActive(command: BasicBooleanCommandDTO) {
    this.writeApiService.changeActive(command);
  }

  changeAddress(command: BasicInt64CommandDTO) {
    this.writeApiService.changeAddress(command);
  }

  changeEmail(command: BasicStringCommandDTO) {
    this.writeApiService.changeEmail(command);
  }

  changeFirstName(command: BasicStringCommandDTO) {
    this.writeApiService.changeFirstName(command);
  }

  changeLastName(command: BasicStringCommandDTO) {
    this.writeApiService.changeLastName(command);
  }

  changePassword(command: BasicStringCommandDTO) {
    this.writeApiService.changePassword(command);
  }

  changeStore(command: BasicInt64CommandDTO) {
    this.writeApiService.changeStore(command);
  }

  changeUsername(command: BasicStringCommandDTO) {
    this.writeApiService.changeUsername(command);
  }

  deleteStaff(command: DeleteStaffCommandDTO) {
    this.writeApiService.deleteStaff(command);
  }
}
