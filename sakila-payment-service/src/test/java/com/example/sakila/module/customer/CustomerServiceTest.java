package com.example.sakila.module.customer;

import com.example.sakila.module.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerService customerService;

  @Test
  void getCustomerById() {
    Customer customer = customerService.getCustomerById(null);

    assertNull(customer);
  }

  @Test
  void getCustomersByStoreId() {
    List<Customer> customers = customerService.getCustomersByStoreId(null);

    assertNull(customers);
  }
}
