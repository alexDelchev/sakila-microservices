package com.example.sakila.module.customer.event.model

import java.time.OffsetDateTime

data class CustomerEventDTO(
    var id: Long? = null,
    var storeId: Long? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var addressId: Long? = null,
    var activeBool: Boolean? = null,
    var createDate: OffsetDateTime? = null,
    var lastUpdate: OffsetDateTime? = null,
    var active: Int? = null
)
