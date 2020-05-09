package com.example.sakila.module.customer.repository

import com.example.sakila.module.customer.Customer

interface CustomerRepository {

  fun getCustomerById(id: Long): Customer?

  fun getCustomersByStoreId(id: Long): List<Customer>?

  fun searchCustomersByFirstName(firstName: String): List<Customer>?

  fun searchCustomersByLastName(lastName: String): List<Customer>?

  fun insertCustomer(customer: Customer): Customer?

  fun updateCustomer(customer: Customer): Customer?

  fun deleteCustomer(customer: Customer)
}
