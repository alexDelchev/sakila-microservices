package com.example.sakila.module.inventory;

import com.example.sakila.generated.server.api.InventoriesApi;
import com.example.sakila.generated.server.model.InventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class InventoryController implements InventoriesApi {

  private final InventoryService inventoryService;

  @Autowired
  public InventoryController(InventoryService inventoryService) {
    this.inventoryService = inventoryService;
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

  private InventoryDTO toDTO(Inventory inventory) {
    InventoryDTO inventoryDTO = new InventoryDTO();
    inventoryDTO.setId(inventory.getId());
    inventoryDTO.setFilmId(inventory.getFilm_id());
    inventoryDTO.setStoreId(inventory.getStore().getId());
    inventoryDTO.setLastUpdate(OffsetDateTime.ofInstant(inventory.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return inventoryDTO;
  }

}
