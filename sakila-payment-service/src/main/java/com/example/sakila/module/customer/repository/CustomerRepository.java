package com.example.sakila.module.customer.repository;

import com.example.sakila.module.customer.Customer;

import java.util.List;

public interface CustomerRepository {

  Customer getCustomerById(Long id);

  List<Customer> getCustomersByStoreId(Long id);

  List<Customer> searchCustomersByFirstName(String firstName);

  List<Customer> searchCustomersByLastName(String lastName);

  Customer insertCustomer(Customer customer);

  Customer updateCustomer(Customer customer);
}
