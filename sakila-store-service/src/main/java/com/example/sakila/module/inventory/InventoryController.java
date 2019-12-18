package com.example.sakila.module.inventory;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.InventoriesApi;
import com.example.sakila.generated.server.model.InventoryDTO;
import com.example.sakila.module.store.Store;
import com.example.sakila.module.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class InventoryController implements InventoriesApi {

  private final InventoryService inventoryService;

  private final StoreService storeService;

  @Autowired
  public InventoryController(InventoryService inventoryService, StoreService storeService) {
    this.inventoryService = inventoryService;
    this.storeService = storeService;
  }

  @Override
  public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(inventoryService.getInventoryById(id)));
  }

  @Override
  public ResponseEntity<List<InventoryDTO>> getInventoriesByStoreId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        inventoryService.getInventoriesByStore(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<InventoryDTO>> getInventoriesByFilmId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        inventoryService.getInventoriesByFilm(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO) {
    return ResponseEntity.ok(toDTO(inventoryService.createInventory(toEntity(inventoryDTO))));
  }

  @Override
  public ResponseEntity<InventoryDTO> replaceInventory(
      @PathVariable("id") Long id, @RequestBody InventoryDTO inventoryDTO
  ) {
    return ResponseEntity.ok(toDTO(inventoryService.updateInventory(id, toEntity(inventoryDTO))));
  }

  @Override
  public ResponseEntity<Void> deleteInventory(@PathVariable("id") Long id) {
    inventoryService.deleteInventory(id);
    return ResponseEntity.ok(null);
  }

  private InventoryDTO toDTO(Inventory inventory) {
    InventoryDTO inventoryDTO = new InventoryDTO();
    inventoryDTO.setId(inventory.getId());
    inventoryDTO.setFilmId(inventory.getFilm_id());
    inventoryDTO.setStoreId(inventory.getStore().getId());
    inventoryDTO.setLastUpdate(OffsetDateTime.ofInstant(inventory.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return inventoryDTO;
  }

  private Inventory toEntity(InventoryDTO inventoryDTO) {
    Inventory inventory = new Inventory();
    inventory.setId(inventoryDTO.getId());
    inventory.setFilm_id(inventoryDTO.getFilmId());

    if (inventoryDTO.getStoreId() != null) {
      Store store = storeService.getStoreById(inventoryDTO.getStoreId());
      if (store == null) throw new NotFoundException("Store for ID " + inventoryDTO.getStoreId() + " does not exist");

      inventory.setStore(store);
    }

    if (inventoryDTO.getLastUpdate() != null) {
      inventory.setLastUpdate(Date.from(inventoryDTO.getLastUpdate().toInstant()));
    }

    return inventory;
  }

}
