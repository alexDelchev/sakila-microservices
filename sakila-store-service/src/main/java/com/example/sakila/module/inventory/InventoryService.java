package com.example.sakila.module.inventory;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

  public Inventory updateInventory(Long id, Inventory source) {
    Inventory target = getInventoryById(id);
    if (target == null) throw new NotFoundException("Inventory for ID " + id + " does not exist");

    target.setFilm_id(source.getFilm_id());
    target.setStore(source.getStore());

    return inventoryRepository.updateInventory(target);
  }

  public void deleteInventory(Long id) {
    Inventory inventory = getInventoryById(id);
    if (inventory == null) throw new NotFoundException("Inventory for ID " + id + " does not exist");

    try {
      inventoryRepository.deleteInventory(inventory);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
