package com.example.sakila.module.customer.event;

import com.example.sakila.module.customer.Customer;
import com.example.sakila.module.customer.event.model.CustomerEventDTO;

public class CustomerEventUtils {

  public static CustomerEventDTO toDTO(Customer customer) {
    CustomerEventDTO customerDTO = new CustomerEventDTO();

    customerDTO.setId(customer.getId());
    customerDTO.setStoreId(customer.getStoreId());
    customerDTO.setFirstName(customer.getFirstName());
    customerDTO.setLastName(customer.getLastName());
    customerDTO.setEmail(customer.getEmail());
    customerDTO.setAddressId(customer.getAddressId());
    customerDTO.setActiveBool(customer.getActiveBool());
    customerDTO.setCreateDate(customer.getCreateDate());
    customerDTO.setLastUpdate(customer.getLastUpdate());
    customerDTO.setActive(customer.getActive());

    return customerDTO;
  }
}
