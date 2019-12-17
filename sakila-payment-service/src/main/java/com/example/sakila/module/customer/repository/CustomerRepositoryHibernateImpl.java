package com.example.sakila.module.customer.repository;

import com.example.sakila.module.customer.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CustomerRepositoryHibernateImpl implements CustomerRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Customer getCustomerById(Long id) {
    return entityManager.find(Customer.class, id);
  }

  @Override
  public List<Customer> getCustomersByStoreId(Long id) {
    TypedQuery<Customer> query = createQuery("SELECT c FROM Customer c WHERE c.store_id = :storeId");
    query.setParameter("storeId", id);
    return query.getResultList();
  }

  @Override
  public List<Customer> searchCustomersByFirstName(String expression) {
    TypedQuery<Customer> query = createQuery(
        "SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(:expression)"
    );
    query.setParameter("expression", '%' + expression + '%');
    return query.getResultList();
  }

  @Override
  public List<Customer> searchCustomersByLastName(String expression) {
    TypedQuery<Customer> query = createQuery(
        "SELECT c FROM Customer c WHERE LOWER(c.lastName) LIKE LOWER(:expression)"
    );
    query.setParameter("expression", '%' + expression + '%');
    return query.getResultList();
  }

  @Override
  @Transactional
  public Customer insertCustomer(Customer customer) {
    entityManager.persist(customer);
    entityManager.flush();
    entityManager.refresh(customer);
    return customer;
  }

  @Override
  @Transactional
  public Customer updateCustomer(Customer customer) {
    entityManager.merge(customer);
    entityManager.flush();
    entityManager.refresh(customer);
    return customer;
  }

  @Override
  @Transactional
  public void deleteCustomer(Customer customer) {
    entityManager.remove(customer);
  }

  private TypedQuery<Customer> createQuery(String sql) {
    return entityManager.createQuery(sql, Customer.class);
  }
}
