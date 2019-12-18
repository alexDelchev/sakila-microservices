package com.example.sakila.module.inventory

import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.inventory.repository.InventoryRepository
import spock.lang.Specification

class InventoryServiceTest extends Specification {

  private InventoryRepository inventoryRepository = Mock(InventoryRepository)

  private InventoryService inventoryService = new InventoryService(inventoryRepository)

  void 'getInventoryById - should return null'() {
    given:
    Inventory inventory

    when:
    inventory = inventoryService.getInventoryById(null)

    then:
    inventory == null
  }

  void 'getInventoriesByStoreId - should return null'() {
    given:
    List<Inventory> inventories

    when:
    inventories = inventoryService.getInventoriesByStore(null)

    then:
    inventories == null
  }

  void 'getInventoriesByFilm - should return null'() {
    given:
    List<Inventory> inventories

    when:
    inventories = inventoryService.getInventoriesByFilm(null)

    then:
    inventories == null
  }

  void 'updateInventory - should throw NotFoundException'() {
    given:
    final long NON_EXISTING_INVENTORY_ID = -1L

    when:
    inventoryService.updateInventory(NON_EXISTING_INVENTORY_ID, new Inventory())

    then:
    thrown NotFoundException
  }

  void 'deleteInventory - should throw NotFoundException'() {
    given:
    final long NON_EXISTING_INVENTORY_ID = -1L;

    when:
    inventoryService.deleteInventory(NON_EXISTING_INVENTORY_ID)

    then:
    thrown NotFoundException
  }
}
