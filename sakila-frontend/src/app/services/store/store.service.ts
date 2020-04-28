import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService as ReadApiService } from '@api/generated/store/read/services/api.service';
import { ApiService as WriteApiService } from '@api/generated/store/write/services/api.service';
import { StoreDTO } from '@api/generated/store/read/models/store-dto';
import { CreateStoreCommandDTO } from '@api/generated/store/write/models/create-store-command-dto';
import { DeleteStoreCommandDTO } from '@api/generated/store/write/models/delete-store-command-dto';
import { BasicInt64CommandDTO } from '@api/generated/store/write/models/basic-int-64command-dto';
import { AggregateIdDTO } from '@api/generated/store/write/models/aggregate-id-dto';

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  constructor(private readApiService: ReadApiService, private writeApiService: WriteApiService) { }

  getStoreById(id: number): Observable<StoreDTO> {
    return this.readApiService.getStoreById(id);
  }

  getStoreByAddressId(id: number): Observable<StoreDTO> {
    return this.readApiService.getStoreByAddressId(id);
  }

  createStore(command: CreateStoreCommandDTO): Observable<AggregateIdDTO> {
    return this.writeApiService.createStore(command);
  }

  deleteStore(command: DeleteStoreCommandDTO) {
    this.writeApiService.deleteStore(command);
  }

  changeAddress(command: BasicInt64CommandDTO) {
    this.writeApiService.changeAddress(command);
  }

  changeManager(command: BasicInt64CommandDTO) {
    this.writeApiService.changeManager(command);
  }
}
