package com.example.sakila.module.customer.event.model

data class CustomerDeletedEvent(
    var customerId: Long? = null
)
