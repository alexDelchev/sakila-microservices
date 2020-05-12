package com.example.sakila.module.customer

import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.DataConflictException
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.customer.event.CustomerEventUtils
import com.example.sakila.module.customer.event.model.CustomerCreatedEvent
import com.example.sakila.module.customer.event.model.CustomerDeletedEvent
import com.example.sakila.module.customer.event.model.CustomerUpdatedEvent
import com.example.sakila.module.customer.repository.CustomerRepository
import com.example.sakila.module.payment.PaymentService
import com.example.sakila.module.rental.RentalService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class CustomerService @Autowired constructor(
    @Qualifier("CustomerEventBus")
    private val eventBus: EventBus,

    private val customerRepository: CustomerRepository,

    private val paymentService: PaymentService,

    private val rentalService: RentalService
) {

  private val log = LoggerFactory.getLogger(CustomerService::class.java)

  fun getCustomerById(id: Long?): Customer? {
    if (id == null) return null

    return customerRepository.getCustomerById(id)
  }

  fun getCustomersByStoreId(id: Long?): List<Customer>? {
    if (id == null) return null

    return customerRepository.getCustomersByStoreId(id)
  }

  fun searchCustomersByFirstName(firstName: String?): List<Customer>? {
    if (firstName == null || firstName.isBlank()) return null

    return customerRepository.searchCustomersByFirstName(firstName)
  }

  fun searchCustomersByLastName(lastName: String?): List<Customer>? {
    if (lastName == null || lastName.isBlank()) return null

    return customerRepository.searchCustomersByLastName(lastName)
  }

  private fun generateCreatedEvent(customer: Customer) {
    val eventDTO = CustomerEventUtils.toDTO(customer)
    val event = CustomerCreatedEvent(eventDTO)
    eventBus.emit(event)
  }

  fun createCustomer(customer: Customer): Customer {
    log.info("Creating Customer")
    val result = customerRepository.insertCustomer(customer)
    log.info("Created Customer id: ${result.id}")

    generateCreatedEvent(result)

    return result
  }

  private fun generateUpdatedEvent(customer: Customer) {
    val eventDTO = CustomerEventUtils.toDTO(customer)
    val event = CustomerUpdatedEvent(eventDTO)
    eventBus.emit(event)
  }

  fun updateCustomer(id: Long, source: Customer): Customer {
    val target = getCustomerById(id) ?: throw NotFoundException("Customer for ID $id does not exist")
    log.info("Updating Customer (ID: $id)")

    target.storeId = source.storeId
    target.firstName = source.firstName
    target.lastName = source.lastName
    target.email = source.email
    target.addressId = source.addressId
    target.active = source.active

    if (source.activeBool != null) target.activeBool = source.activeBool
    if (source.createDate != null) target.createDate = source.createDate

    val result = customerRepository.updateCustomer(target)

    generateUpdatedEvent(result)

    return result
  }

  private fun generateDeletedEvent(id: Long) {
    val event = CustomerDeletedEvent(id)
    eventBus.emit(event)
  }

  private fun deletePayments(customerId: Long) {
    paymentService.getPaymentsByCustomerId(customerId)?.forEach { paymentService.deletePayment(it.id!!) }
  }

  private fun deleteRentals(customerId: Long) {
    rentalService.getRentalsByCustomerId(customerId)?.forEach { rentalService.deleteRental(it.id!!) }
  }

  fun deleteCustomer(id: Long) {
    val customer = getCustomerById(id) ?: throw NotFoundException("Customer for ID $id does not exist")
    log.info("Deleting Customer (ID: $id)")

    try {
      deletePayments(id)
      deleteRentals(id)
      customerRepository.deleteCustomer(customer)
      generateDeletedEvent(id)
    } catch (e: DataIntegrityViolationException) {
      throw DataConflictException(e.message?: "Failed to delete Customer with ID $id")
    }
  }

}
