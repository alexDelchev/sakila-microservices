package com.example.sakila.module.payment.event

import com.example.sakila.module.payment.Payment
import com.example.sakila.module.payment.event.model.PaymentEventDTO

class PaymentEventUtils {

  companion object {

    @JvmStatic
    fun toDTO(payment: Payment): PaymentEventDTO {
      val dto = PaymentEventDTO()

      dto.id = payment.id
      dto.amount = payment.amount
      dto.paymentDate = payment.paymentDate
      dto.staffId = payment.staffId
      dto.customerId = payment.customer?.id?:
          throw IllegalArgumentException("Payment with id ${payment.id} has no customer")
      dto.rentalId = payment.rental?.id?:
          throw IllegalArgumentException("Payment with id ${payment.id} has no rental")

      return dto
    }

  }
}
