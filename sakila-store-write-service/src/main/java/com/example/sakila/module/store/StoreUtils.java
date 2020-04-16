package com.example.sakila.module.store;

public class StoreUtils {

  public static StoreDTO toDTO(StoreWriteModel model) {
    StoreDTO dto = new StoreDTO();

    dto.setId(model.getId());
    dto.setAddressId(model.getAddressId());
    dto.setManagerStaffId(model.getManagerStaffId());
    dto.setLastUpdate(model.getLastUpdate());

    return dto;
  }
}
