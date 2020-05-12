package com.example.sakila.module.rental

import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.DataConflictException
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.payment.PaymentService
import com.example.sakila.module.rental.event.RentalEventUtils
import com.example.sakila.module.rental.event.model.RentalCreatedEvent
import com.example.sakila.module.rental.event.model.RentalDeletedEvent
import com.example.sakila.module.rental.event.model.RentalUpdatedEvent
import com.example.sakila.module.rental.repository.RentalRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class RentalService @Autowired constructor(
    @Qualifier("RentalEventBus")
    private val eventBus: EventBus,

    private val rentalRepository: RentalRepository,

    private val paymentService: PaymentService
) {

  private val log = LoggerFactory.getLogger(RentalService::class.java)

  fun getRentalById(id: Long?): Rental? {
    if (id == null) return null
    return rentalRepository.getRentalById(id)
  }

  fun getRentalsByCustomerId(id: Long?): List<Rental>? {
    if (id == null) return null
    return rentalRepository.getRentalsByCustomerId(id)
  }

  fun getRentalsByStaffId(id: Long?): List<Rental>? {
    if (id == null) return null
    return rentalRepository.getRentalsByStaffId(id)
  }

  private fun generateCreatedEvent(rental: Rental) {
    val dto = RentalEventUtils.toDTO(rental)
    val event = RentalCreatedEvent(dto)
    eventBus.emit(event)
  }

  fun createRental(rental: Rental): Rental {
    log.info("Creating Rental")
    val result = rentalRepository.insertRental(rental)
    log.info("Created Rental id: ${result.id}")

    generateCreatedEvent(result)

    return result
  }

  private fun generateUpdatedEvent(rental: Rental) {
    val dto = RentalEventUtils.toDTO(rental)
    val event = RentalUpdatedEvent(dto)
    eventBus.emit(event)
  }

  fun updateRental(id: Long, source: Rental): Rental {
    val target = getRentalById(id)?: throw NotFoundException("Rental for ID $id does not exist")
    log.info("Updating Rental (ID: $id)")

    target.rentalDate = source.rentalDate
    target.returnDate = source.returnDate
    target.customer = source.customer
    target.filmId = source.filmId
    target.storeId = source.storeId
    target.staffId = source.staffId

    val result = rentalRepository.updateRental(target)

    generateUpdatedEvent(result)

    return result
  }

  private fun generateDeletedEvent(id: Long) {
    val event = RentalDeletedEvent(id)
    eventBus.emit(event)
  }

  private fun deletePayments(rentalId: Long)  {
    paymentService.getPaymentsByRentalId(rentalId)?.forEach { paymentService.deletePayment(it.id!!) }
  }

  fun deleteRental(id: Long) {
    val rental = rentalRepository.getRentalById(id)?: throw NotFoundException("Rental for ID $id does not exist")
    log.info("Deleting Rental (ID: $id)")

    try {
      deletePayments(id)
      rentalRepository.deleteRental(rental)
      generateDeletedEvent(id)
    } catch (e: DataIntegrityViolationException) {
      throw DataConflictException(e.message?: "Failed deleting rental with id: $id")
    }
  }
}
