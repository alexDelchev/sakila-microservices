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

  private StoreDTO toDTO(Store store) {
    StoreDTO storeDTO = new StoreDTO();

    storeDTO.setId(store.getId());
    storeDTO.setManagerStaffId(store.getManagerStaffId());
    storeDTO.setAddressId(store.getAddressId());
    storeDTO.setLastUpdate(store.getLastUpdate());

    return storeDTO;
  }

  private Store toEntity(StoreDTO storeDTO) {
    Store store = new Store();

    store.setId(storeDTO.getId());
    store.setAddressId(storeDTO.getAddressId());
    store.setManagerStaffId(storeDTO.getManagerStaffId());
    store.setLastUpdate(storeDTO.getLastUpdate());

    return store;
  }
}
