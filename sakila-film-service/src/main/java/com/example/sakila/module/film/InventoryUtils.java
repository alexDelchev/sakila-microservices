package com.example.sakila.module.film;

import com.example.sakila.generated.server.model.FilmInventoryDTO;

public class InventoryUtils {

  public static Inventory toEntity(FilmInventoryDTO dto) {
    Inventory inventory = new Inventory();

    inventory.setStoreId(dto.getStoreId());
    inventory.setQuantity(dto.getQuantity());

    return inventory;
  }

  public static FilmInventoryDTO toDTO(Inventory inventory) {
    FilmInventoryDTO dto = new FilmInventoryDTO();

    dto.setStoreId(inventory.getStoreId());
    dto.setQuantity(inventory.getQuantity());

    return dto;
  }
}
