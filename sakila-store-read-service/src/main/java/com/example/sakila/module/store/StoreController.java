package com.example.sakila.module.store;

import com.example.sakila.generated.server.api.StoresApi;
import com.example.sakila.generated.server.model.StoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class StoreController implements StoresApi {

  private final StoreService storeService;

  @Autowired
  public StoreController(StoreService storeService) {
    this.storeService = storeService;
  }

  @Override
  public ResponseEntity<StoreDTO> getStoreById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(storeService.getStoreById(id)));
  }

  @Override
  public ResponseEntity<StoreDTO> getStoreByAddressId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(storeService.getStoreByAddressId(id)));
  }

  @Override
  public ResponseEntity<StoreDTO> createStore(@RequestBody StoreDTO storeDTO) {
    return ResponseEntity.ok(toDTO(storeService.createStore(toEntity(storeDTO))));
  }

  @Override
  public ResponseEntity<StoreDTO> replaceStore(@PathVariable("id") Long id, @RequestBody StoreDTO storeDTO) {
    return ResponseEntity.ok(toDTO(storeService.updateStore(id, toEntity(storeDTO))));
  }

  @Override
  public ResponseEntity<Void> deleteStore(@PathVariable("id") Long id) {
    storeService.deleteStore(id);
    return ResponseEntity.ok(null);
  }

  private StoreDTO toDTO(Store store) {
    StoreDTO storeDTO = new StoreDTO();

    storeDTO.setId(store.getId());
    storeDTO.setManagerStaffId(store.getManagerStaffId());
    storeDTO.setAddressId(store.getAddressId());
    if (store.getLastUpdate() != null) {
      storeDTO.setLastUpdate(
          OffsetDateTime.ofInstant(Instant.ofEpochMilli(store.getLastUpdate().getTime()), ZoneId.systemDefault())
      );
    }

    return storeDTO;
  }

  private Store toEntity(StoreDTO storeDTO) {
    Store store = new Store();

    store.setId(storeDTO.getId());
    store.setAddressId(storeDTO.getAddressId());
    store.setManagerStaffId(storeDTO.getManagerStaffId());
    if (storeDTO.getLastUpdate() != null) store.setLastUpdate(Date.from(storeDTO.getLastUpdate().toInstant()));

    return store;
  }
}
