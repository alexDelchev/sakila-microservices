package com.example.sakila.module.store;

import com.example.sakila.generated.server.api.StoresApi;
import com.example.sakila.generated.server.model.StoreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;

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

  private StoreDTO toDTO(Store store) {
    StoreDTO storeDTO = new StoreDTO();
    storeDTO.setId(store.getId());
    storeDTO.setManagerStaffId(store.getManagerStaff().getId());
    storeDTO.setAddressId(store.getAddress_id());
    storeDTO.setLastUpdate(OffsetDateTime.ofInstant(store.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return storeDTO;
  }
}
