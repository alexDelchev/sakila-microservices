package com.example.sakila.module.payment

import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.DataConflictException
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.payment.event.PaymentEventUtils
import com.example.sakila.module.payment.event.model.PaymentCreatedEvent
import com.example.sakila.module.payment.event.model.PaymentDeletedEvent
import com.example.sakila.module.payment.event.model.PaymentUpdatedEvent
import com.example.sakila.module.payment.repository.PaymentRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class PaymentService @Autowired constructor(
    @Qualifier("PaymentEventBus")
    private val eventBus: EventBus,

    private val paymentRepository: PaymentRepository
) {

  private val log = LoggerFactory.getLogger(PaymentService::class.java)

  fun getPaymentById(id: Long?): Payment? {
    if (id == null) return null
    return paymentRepository.getPaymentById(id)
  }

  fun getPaymentsByRentalId(id: Long?): List<Payment>? {
    if (id == null) return null
    return paymentRepository.getPaymentsByRentalId(id)
  }

  fun getPaymentsByCustomerId(id: Long?): List<Payment>? {
    if (id == null) return null
    return paymentRepository.getPaymentsByCustomerId(id)
  }

  fun getPaymentsByStaffId(id: Long?): List<Payment>? {
    if (id == null) return null
    return paymentRepository.getPaymentsByStaffId(id)
  }

  private fun generateCreatedEvent(payment: Payment) {
    val dto = PaymentEventUtils.toDTO(payment)
    val event = PaymentCreatedEvent()
    event.dto = dto
    eventBus.emit(event)
  }

  fun createPayment(payment: Payment): Payment {
    log.info("Creating Payment")
    val result = paymentRepository.insertPayment(payment)
    log.info("Created Payment id: ${result.id}")

    generateCreatedEvent(payment)

    return result
  }

  private fun generateUpdatedEvent(payment: Payment) {
    val dto = PaymentEventUtils.toDTO(payment)
    val event = PaymentUpdatedEvent()
    event.dto = dto
    eventBus.emit(event)
  }

  fun updatePayment(id: Long, source: Payment): Payment {
    val target = getPaymentById(id)?: throw NotFoundException("Payment for ID $id does not exist")
    log.info("Updating Payment (ID: $id)")

    target.customer = source.customer
    target.rental = source.rental
    target.staffId = source.staffId
    target.amount = source.amount
    target.paymentDate = source.paymentDate

    val result = paymentRepository.updatePayment(target)

    generateUpdatedEvent(result)

    return result
  }

  private fun generateDeletedEvent(id: Long) {
    val event = PaymentDeletedEvent()
    event.paymentId = id
    eventBus.emit(event)
  }

  fun deletePayment(id: Long) {
    val payment = getPaymentById(id)?: throw NotFoundException("Payment for ID $id does not exist")
    log.info("Deleting Payment (ID: $id)")

    try {
      paymentRepository.deletePayment(payment)
      generateDeletedEvent(id)
    } catch (e: DataIntegrityViolationException) {
      throw DataConflictException(e.message?: "Failed to delete Payment with ID $id", e)
    }
  }

}
