package com.example.sakila.module.payment

import com.example.sakila.exception.BadRequestException
import com.example.sakila.exception.NotFoundException
import com.example.sakila.generated.server.api.PaymentsApi
import com.example.sakila.generated.server.model.PaymentDTO
import com.example.sakila.module.customer.CustomerService
import com.example.sakila.module.rental.RentalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class PaymentController @Autowired constructor(
    private val paymentService: PaymentService,
    private val customerService: CustomerService,
    private val rentalService: RentalService
) : PaymentsApi {

  @RequestMapping(
      value = ["/payments/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun getPaymentById(@PathVariable("id") id: Long): ResponseEntity<PaymentDTO?> {
    val payment = paymentService.getPaymentById(id)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(toDTO(payment))
  }

  @RequestMapping(
      value = ["/payments/rental/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun getPaymentsByRentalId(@PathVariable("id") id: Long): ResponseEntity<List<PaymentDTO>?> {
    val payments = paymentService.getPaymentsByRentalId(id)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(payments.map { toDTO(it) })
  }

  @RequestMapping(
      value = ["/payments/customer/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun getPaymentsByCustomerId(@PathVariable("id") id: Long): ResponseEntity<List<PaymentDTO>?> {
    val payments = paymentService.getPaymentsByCustomerId(id)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(payments.map{ toDTO(it) })
  }

  @RequestMapping(
      value = ["/payments/staff/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun getPaymentsByStaffId(@PathVariable("id") id: Long): ResponseEntity<List<PaymentDTO>?> {
    val payments = paymentService.getPaymentsByStaffId(id)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(payments.map { toDTO(it) })
  }

  @RequestMapping(
      value = ["/payments"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.POST]
  ) override fun createPayment(@RequestBody paymentDTO: PaymentDTO): ResponseEntity<PaymentDTO> {
    var payment = toEntity(paymentDTO)
    payment = paymentService.createPayment(payment)

    val result = toDTO(payment)
    return ResponseEntity.ok(result)
  }

  @RequestMapping(
      value = ["/payments/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.PUT]
  ) override fun replacePayment(id: Long?, paymentDTO: PaymentDTO): ResponseEntity<PaymentDTO> {
    if (id == null) throw BadRequestException("ID should not be null")
    var payment = toEntity(paymentDTO)
    payment = paymentService.updatePayment(id, payment)

    val result = toDTO(payment)
    return ResponseEntity.ok(result)
  }

  @RequestMapping(
      value = ["/payments/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.DELETE]
  ) override fun deletePayment(id: Long?): ResponseEntity<Void> {
    if (id == null) throw BadRequestException("ID should not be null")
    paymentService.deletePayment(id)
    return ResponseEntity.ok().build()
  }

  private fun toDTO(payment: Payment): PaymentDTO {
    val paymentDTO = PaymentDTO()

    paymentDTO.id = payment.id
    paymentDTO.staffId = paymentDTO.staffId
    paymentDTO.amount = paymentDTO.amount
    paymentDTO.paymentDate = payment.paymentDate

    paymentDTO.customerId = payment.customer?.id
    paymentDTO.rentalId = payment.rental?.id

    return paymentDTO
  }

  private fun toEntity(paymentDTO: PaymentDTO): Payment {
    val payment = Payment()

    payment.id = paymentDTO.id
    payment.amount = paymentDTO.amount
    payment.staffId = paymentDTO.staffId

    if (paymentDTO.customerId != null) {
      val customerId = paymentDTO.customerId
      val customer = customerService.getCustomerById(customerId)?:
        throw NotFoundException("Customer for ID $customerId does not exist")

      payment.customer = customer
    }

    if (paymentDTO.rentalId != null) {
      val rentalId = paymentDTO.rentalId
      val rental = rentalService.getRentalById(rentalId)?:
        throw NotFoundException("Rental for ID $rentalId does not exist")

      payment.rental = rental
    }

    payment.paymentDate = paymentDTO.paymentDate

    return payment
  }
}
