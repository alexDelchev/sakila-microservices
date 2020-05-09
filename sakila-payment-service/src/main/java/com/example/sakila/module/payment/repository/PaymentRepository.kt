package com.example.sakila.module.payment.repository

import com.example.sakila.module.payment.Payment

interface PaymentRepository {

  fun getPaymentById(id: Long?): Payment?

  fun getPaymentsByRentalId(id: Long?): List<Payment>?

  fun getPaymentsByCustomerId(id: Long?): List<Payment>?

  fun getPaymentsByStaffId(id: Long?): List<Payment>?

  fun insertPayment(payment: Payment): Payment

  fun updatePayment(payment: Payment): Payment

  fun deletePayment(payment: Payment)

}
