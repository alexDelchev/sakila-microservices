package com.example.sakila.module.film.event;

import com.example.sakila.module.film.Inventory;
import com.example.sakila.module.film.event.model.InventoryEventDTO;

public class InventoryEventUtils {

  public static InventoryEventDTO toDTO(Inventory inventory) {
    InventoryEventDTO dto = new InventoryEventDTO();

    dto.setStoreId(inventory.getStoreId());
    dto.setQuantity(inventory.getQuantity());

    return dto;
  }
}
