package com.example.sakila.module.inventory

import com.example.sakila.module.store.StoreService
import spock.lang.Specification

class InventoryControllerTest extends Specification {

  private InventoryService inventoryService = Mock(InventoryService)

  private StoreService storeService = Mock(StoreService)

  private InventoryController inventoryController = new InventoryController(inventoryService, storeService)
  
}
