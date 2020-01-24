import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { ApiService } from '@api/generated/store/services/api.service';
import { InventoryDTO } from '@api/generated/store/models/inventory-dto';

@Injectable({
  providedIn: 'root'
})
export class InventoryService {

  constructor(private apiService: ApiService) { }

  getInventoryById(id: number): Observable<InventoryDTO> {
    return this.apiService.getInventoryById(id);
  }

  getInventoriesByStoreId(id: number): Observable<Array<InventoryDTO>> {
    return this.apiService.getInventoriesByStoreId(id);
  }

  getInventoriesByFilmId(id: number): Observable<Array<InventoryDTO>> {
    return this.apiService.getInventoriesByFilmId(id);
  }

  createInventory(inventory: InventoryDTO): Observable<InventoryDTO> {
    return this.apiService.createInventory(inventory);
  }

  replaceInventory(id: number, inventory: InventoryDTO): Observable<InventoryDTO> {
    return this.apiService.replaceInventory({ id: id, inventoryDTO: inventory });
  }

  deleteInventory(id: number) {
    this.apiService.deleteInventory(id);
  }
}
