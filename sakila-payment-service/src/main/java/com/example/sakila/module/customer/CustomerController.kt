package com.example.sakila.module.customer

import com.example.sakila.generated.server.api.CustomersApi
import com.example.sakila.generated.server.model.CustomerDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CustomerController @Autowired constructor(
    private val customerService: CustomerService
) : CustomersApi {

  @RequestMapping(
      value = ["/customers/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun getCustomerById(@PathVariable("id") id: Long): ResponseEntity<CustomerDTO?> {
    val customer = customerService.getCustomerById(id)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(toDTO(customer))
  }

  @RequestMapping(
      value = ["/customers/store/{id}"],
      produces = ["application/json" ],
      consumes = ["application/json" ],
      method = [RequestMethod.GET]
  ) override fun getCustomersByStoreId(@PathVariable("id") id: Long): ResponseEntity<List<CustomerDTO>?> {
    val customers = customerService.getCustomersByStoreId(id)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(customers.map { toDTO(it) })
  }

  @RequestMapping(
      value = ["/customers/search/firstName"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun searchCustomersByFirstName(
      @RequestParam(value = "expression", required = false) firstName: String
  ): ResponseEntity<List<CustomerDTO>?> {
    val customers = customerService.searchCustomersByFirstName(firstName)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(customers.map { toDTO(it) })
  }

  @RequestMapping(
      value = ["/customers/search/lastName"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun searchCustomersByLastName(
      @RequestParam(value = "expression", required = false) lastName: String
  ): ResponseEntity<List<CustomerDTO>?> {
    val customers = customerService.searchCustomersByLastName(lastName)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(customers.map { toDTO(it) })
  }

  @RequestMapping(
      value = ["/customers"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.POST]
  ) override fun createCustomer(@RequestBody customerDTO: CustomerDTO): ResponseEntity<CustomerDTO> {
    var customer = toEntity(customerDTO)
    customer = customerService.createCustomer(customer)

    val result = toDTO(customer)
    return ResponseEntity.ok(result)
  }

  @RequestMapping(
      value = ["/customers/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.PUT]
  ) override fun replaceCustomer(
      @PathVariable("id") id: Long,
      @RequestBody customerDTO: CustomerDTO
  ): ResponseEntity<CustomerDTO> {
    var customer = toEntity(customerDTO)
    customer = customerService.updateCustomer(id, customer)

    val result = toDTO(customer)
    return ResponseEntity.ok(result)
  }

  @RequestMapping(
      value = ["/customers/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.DELETE]
  ) override fun deleteCustomer(id: Long): ResponseEntity<Void> {
    customerService.deleteCustomer(id)
    return ResponseEntity.ok().build()
  }

  private fun toDTO(customer: Customer): CustomerDTO {
    val customerDTO = CustomerDTO()

    customerDTO.id = customer.id
    customerDTO.storeId = customer.storeId
    customerDTO.firstName = customer.firstName
    customerDTO.lastName = customer.lastName
    customerDTO.email = customer.email
    customerDTO.addressId = customer.addressId
    customerDTO.isActiveBool = customer.activeBool
    customerDTO.createDate = customer.createDate
    customerDTO.lastUpdate = customer.lastUpdate
    customerDTO.active = customer.active

    return customerDTO
  }

  private fun toEntity(customerDTO: CustomerDTO): Customer {
    val customer = Customer()

    customer.id = customerDTO.id
    customer.storeId = customerDTO.storeId
    customer.firstName = customerDTO.firstName
    customer.lastName = customerDTO.lastName
    customer.email = customerDTO.email
    customer.addressId = customerDTO.addressId
    customer.activeBool = customerDTO.isActiveBool
    customer.createDate = customerDTO.createDate
    customer.lastUpdate = customerDTO.lastUpdate
    customer.active = customerDTO.active

    return customer
  }

}
