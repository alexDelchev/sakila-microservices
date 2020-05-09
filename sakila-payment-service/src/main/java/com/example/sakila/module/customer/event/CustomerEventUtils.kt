package com.example.sakila.module.customer.event

import com.example.sakila.module.customer.Customer
import com.example.sakila.module.customer.event.model.CustomerEventDTO

class CustomerEventUtils {

  companion object {

      @JvmStatic
      fun toDTO(customer: Customer): CustomerEventDTO {
        val customerEventDTO = CustomerEventDTO()

        customerEventDTO.id = customer.id
        customerEventDTO.storeId = customer.storeId
        customerEventDTO.firstName = customer.firstName
        customerEventDTO.lastName = customer.lastName
        customerEventDTO.email = customer.email
        customerEventDTO.addressId = customer.addressId
        customerEventDTO.activeBool = customer.activeBool
        customerEventDTO.createDate = customer.createDate
        customerEventDTO.lastUpdate = customer.lastUpdate
        customerEventDTO.active = customer.active

        return customerEventDTO
      }
  }

}
