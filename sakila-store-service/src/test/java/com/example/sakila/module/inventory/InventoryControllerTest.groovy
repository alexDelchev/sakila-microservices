package com.example.sakila.module.inventory

import com.example.sakila.exception.NotFoundException
import com.example.sakila.generated.server.model.InventoryDTO
import com.example.sakila.module.store.StoreService
import spock.lang.Specification

class InventoryControllerTest extends Specification {

  private InventoryService inventoryService = Mock(InventoryService)

  private StoreService storeService = Mock(StoreService)

  private InventoryController inventoryController = new InventoryController(inventoryService, storeService)

  void 'createInventory - should throw NotFoundException when the DTO references non-existing data'() {
    given:
    final long NON_EXISTING_STORE_ID = -1L
    InventoryDTO inventoryDTO = new InventoryDTO()
    inventoryDTO.setStoreId(NON_EXISTING_STORE_ID)

    when:
    inventoryController.createInventory(inventoryDTO)

    then:
    thrown NotFoundException
  }

  void 'replaceInventory - should throw NotFoundException when the DTO references non-existing data'() {
    given:
    final long NON_EXISTING_STORE_ID = -1L
    InventoryDTO inventoryDTO = new InventoryDTO()
    inventoryDTO.setStoreId(NON_EXISTING_STORE_ID)

    when:
    inventoryController.replaceInventory(1L, inventoryDTO)

    then:
    thrown NotFoundException
  }
}
