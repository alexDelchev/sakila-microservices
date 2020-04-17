package com.example.sakila.module.staff;

public class StaffUtils {

  public static StaffDTO toDTO(StaffWriteModel model) {
    StaffDTO dto = new StaffDTO();

    dto.setId(model.getId());
    dto.setFirstName(model.getFirstName());
    dto.setLastName(model.getLastName());
    dto.setAddressId(model.getAddressId());
    dto.setEmail(model.getEmail());
    dto.setStoreId(model.getStoreId());
    dto.setActive(model.getActive());
    dto.setUserName(model.getUserName());
    dto.setPassword(model.getPassword());

    return dto;
  }
}
