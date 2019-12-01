package com.example.sakila.module.customer;

import com.example.sakila.generated.server.api.CustomersApi;
import com.example.sakila.generated.server.model.CustomerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CustomerController implements CustomersApi {

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(customerService.getCustomerById(id)));
  }

  @Override
  public ResponseEntity<List<CustomerDTO>> getCustomersByStoreId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        customerService.getCustomersByStoreId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<CustomerDTO>> searchCustomersByFirstName(
      @RequestParam(value = "expression", required = false) String expression
  ) {
    return ResponseEntity.ok(
        customerService.searchCustomersByFirstName(expression).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  private CustomerDTO toDTO(Customer customer) {
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setId(customer.getId());
    customerDTO.setStoreId(customer.getStore_id());
    customerDTO.setFirstName(customer.getFirstName());
    customerDTO.setLastName(customer.getLastName());
    customerDTO.setEmail(customer.getEmail());
    customerDTO.setAddressId(customer.getAddress_id());
    customerDTO.setActiveBool(customer.getActiveBool());
    customerDTO.setCreateDate(toLocalDate(customer.getCreateDate()));
    customerDTO.setLastUpdate(toOffsetDateTime(customer.getLastUpdate()));
    customerDTO.setActive(customer.getActive());
    return customerDTO;
  }

  private LocalDate toLocalDate(Date date) {
    return ((java.sql.Date) date).toLocalDate();
  }

  private OffsetDateTime toOffsetDateTime(Date date) {
    return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }
}
