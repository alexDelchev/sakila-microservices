package com.example.sakila.module.rental.event.model

import java.time.OffsetDateTime

data class RentalEventDTO(
    var id: Long? = null,
    var rentalDate: OffsetDateTime? = null,
    var filmId: String? = null,
    var storeId: Long? = null,
    var customerId: Long? = null,
    var returnDate: OffsetDateTime? = null,
    var staffId: Long? = null,
    var lastUpdate: OffsetDateTime? = null
)
