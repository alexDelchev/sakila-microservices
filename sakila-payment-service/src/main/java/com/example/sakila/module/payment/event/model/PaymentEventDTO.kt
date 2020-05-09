package com.example.sakila.module.payment.event.model

import java.time.OffsetDateTime

data class PaymentEventDTO(
    var id: Long? = null,
    var customerId: Long? = null,
    var staffId: Long? = null,
    var rentalId: Long? = null,
    var amount: Float? = null,
    var paymentDate: OffsetDateTime? = null
)
