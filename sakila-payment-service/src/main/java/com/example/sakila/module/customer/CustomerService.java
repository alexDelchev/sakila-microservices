package com.example.sakila.module.customer;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Customer getCustomerById(Long id) {
    if (id == null) return null;
    return customerRepository.getCustomerById(id);
  }

  public List<Customer> getCustomersByStoreId(Long id) {
    if (id == null) return null;
    return customerRepository.getCustomersByStoreId(id);
  }

  public List<Customer> searchCustomersByFirstName(String expression) {
    if (expression == null || expression.length() == 0) return null;
    return customerRepository.searchCustomersByFirstName(expression);
  }

  public List<Customer> searchCustomersByLastName(String expression) {
    if (expression == null || expression.length() == 0) return null;
    return customerRepository.searchCustomersByLastName(expression);
  }

  public Customer createCustomer(Customer customer) {
    return customerRepository.insertCustomer(customer);
  }

  public Customer updateCustomer(Long id, Customer source) {
    Customer target = getCustomerById(id);
    if (target == null) throw new NotFoundException("Customer for ID " + id + " does not exist");

    target.setStore_id(source.getStore_id());
    target.setFirstName(source.getFirstName());
    target.setLastName(source.getLastName());
    target.setEmail(source.getEmail());
    target.setAddress_id(source.getAddress_id());
    target.setActive(source.getActive());

    //Update these values only when they are not null as columns prohibit nulls and have default values
    if (source.getActiveBool() != null) target.setActiveBool(source.getActiveBool());
    if (source.getCreateDate() != null) target.setCreateDate(source.getCreateDate());

    return customerRepository.updateCustomer(target);
  }
}
