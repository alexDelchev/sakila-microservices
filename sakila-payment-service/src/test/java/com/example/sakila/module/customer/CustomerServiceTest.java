package com.example.sakila.module.customer;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

  @Mock
  private EventBus eventBus;

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

  @Test
  void updateCustomer() {
    final long existingCustomerId = 1L;
    when(customerRepository.getCustomerById(existingCustomerId)).thenReturn(new Customer());

    final long nonExistingCustomerId = -1L;
    when(customerRepository.getCustomerById(nonExistingCustomerId)).thenReturn(null);

    when(customerRepository.updateCustomer(any(Customer.class))).thenReturn(new Customer());

    assertDoesNotThrow(() -> customerService.updateCustomer(existingCustomerId, new Customer()));
    assertThrows(
        NotFoundException.class,
        () -> customerService.updateCustomer(nonExistingCustomerId, new Customer())
    );
  }

  @Test
  void deleteCustomer() {
    final long nonExistingCustomerId = 1L;

    assertThrows(NotFoundException.class, () -> customerService.deleteCustomer(nonExistingCustomerId));
  }
}
