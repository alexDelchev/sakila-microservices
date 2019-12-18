package com.example.sakila.module.inventory.repository;

import com.example.sakila.module.inventory.Inventory;

import java.util.List;

public interface InventoryRepository {

  Inventory getInventoryById(Long id);

  List<Inventory> getInventoriesByStore(Long storeId);

  List<Inventory> getInventoriesByFilm(Long filmId);

  Inventory insertInventory(Inventory inventory);

  Inventory updateInventory(Inventory inventory);
}
