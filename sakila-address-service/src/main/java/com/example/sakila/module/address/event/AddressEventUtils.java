package com.example.sakila.module.address.event;

import com.example.sakila.module.address.Address;
import com.example.sakila.module.address.event.model.AddressEventDTO;
import com.example.sakila.module.city.event.CityEventUtils;

public class AddressEventUtils {

  public static AddressEventDTO toDto(Address address) {
    AddressEventDTO dto = new AddressEventDTO();

    dto.setId(address.getId());
    dto.setAddress(address.getAddress());
    dto.setAddress2(address.getAddress2());
    dto.setDistrict(address.getDistrict());
    dto.setPostalCode(address.getPostalCode());
    dto.setPhone(address.getPhone());
    dto.setLastUpdate(address.getLastUpdate());

    if (address.getCity() != null) dto.setCity(CityEventUtils.toDto(address.getCity()));

    return dto;
  }
}
