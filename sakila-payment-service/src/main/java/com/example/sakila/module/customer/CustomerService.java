package com.example.sakila.module.customer;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.customer.event.CustomerEventUtils;
import com.example.sakila.module.customer.event.model.CustomerCreatedEvent;
import com.example.sakila.module.customer.event.model.CustomerEventDTO;
import com.example.sakila.module.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

  private final EventBus eventBus;

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerService(@Qualifier("CustomerEventBus") EventBus eventBus, CustomerRepository customerRepository) {
    this.eventBus = eventBus;
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

  private void generateCreatedEvent(Customer customer) {
    CustomerEventDTO eventDTO = CustomerEventUtils.toDTO(customer);
    CustomerCreatedEvent createdEvent = new CustomerCreatedEvent();
    createdEvent.setDto(eventDTO);
    eventBus.emit(createdEvent);
  }

  public Customer createCustomer(Customer customer) {
    Customer result = customerRepository.insertCustomer(customer);

    generateCreatedEvent(result);

    return result;
  }

  public Customer updateCustomer(Long id, Customer source) {
    Customer target = getCustomerById(id);
    if (target == null) throw new NotFoundException("Customer for ID " + id + " does not exist");

    target.setStoreId(source.getStoreId());
    target.setFirstName(source.getFirstName());
    target.setLastName(source.getLastName());
    target.setEmail(source.getEmail());
    target.setAddressId(source.getAddressId());
    target.setActive(source.getActive());

    //Update these values only when they are not null as columns prohibit nulls and have default values
    if (source.getActiveBool() != null) target.setActiveBool(source.getActiveBool());
    if (source.getCreateDate() != null) target.setCreateDate(source.getCreateDate());

    return customerRepository.updateCustomer(target);
  }

  public void deleteCustomer(Long id) {
    Customer customer = getCustomerById(id);
    if (customer == null) throw new NotFoundException("Customer for ID " + id + " does not exist");

    try {
      customerRepository.deleteCustomer(customer);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
