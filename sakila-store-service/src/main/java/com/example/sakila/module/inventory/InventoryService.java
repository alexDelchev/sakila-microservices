package com.example.sakila.module.inventory;

import com.example.sakila.module.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

  private final InventoryRepository inventoryRepository;

  @Autowired
  public InventoryService(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  public Inventory getInventoryById(Long id) {
    if (id == null) return null;
    return inventoryRepository.getInventoryById(id);
  }

  public List<Inventory> getInventoriesByStore(Long storeId) {
    if (storeId == null) return null;
    return inventoryRepository.getInventoriesByStore(storeId);
  }

  public List<Inventory> getInventoriesByFilm(Long filmId) {
    if (filmId == null) return null;
    return inventoryRepository.getInventoriesByFilm(filmId);
  }

  public Inventory createInventory(Inventory inventory) {
    return inventoryRepository.insertInventory(inventory);
  }
}
