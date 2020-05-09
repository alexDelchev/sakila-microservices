package com.example.sakila.module.customer.repository

import com.example.sakila.module.customer.Customer
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class CustomerRepositoryHibernateImpl : CustomerRepository {

  @PersistenceContext
  private lateinit var entityManager: EntityManager

  override fun getCustomerById(id: Long): Customer? = entityManager.find(Customer::class.java, id)

  override fun getCustomersByStoreId(id: Long): List<Customer>? {
    val query = createQuery("SELECT c FROM Customer c WHERE c.storeId = :storeId")
    query.setParameter("storeId", id)
    return query.resultList
  }

  override fun searchCustomersByFirstName(firstName: String): List<Customer>? {
    val query = createQuery("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(:expression)")
    query.setParameter("expression", "%$firstName%")
    return query.resultList
  }

  override fun searchCustomersByLastName(lastName: String): List<Customer>? {
    val query = createQuery("SELECT c FROM Customer c WHERE LOWER(c.lastName) LIKE LOWER(:expression)")
    query.setParameter("expression", "%$lastName%")
    return query.resultList
  }

  @Transactional
  override fun insertCustomer(customer: Customer): Customer {
    entityManager.persist(customer)
    entityManager.flush()
    entityManager.refresh(customer)
    return customer
  }

  @Transactional
  override fun updateCustomer(customer: Customer): Customer {
    entityManager.merge(customer)
    entityManager.flush()
    entityManager.refresh(customer)
    return customer
  }

  @Transactional
  override fun deleteCustomer(customer: Customer) = entityManager.remove(customer)

  private fun createQuery(sql: String): TypedQuery<Customer> = entityManager.createQuery(sql, Customer::class.java)

}
