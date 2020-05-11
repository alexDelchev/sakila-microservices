package com.example.sakila.module.rental

import com.example.sakila.exception.BadRequestException
import com.example.sakila.exception.NotFoundException
import com.example.sakila.generated.server.api.RentalsApi
import com.example.sakila.generated.server.model.RentalDTO
import com.example.sakila.module.customer.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
class RentalController @Autowired constructor(
    private val rentalService: RentalService,

    private val customerService: CustomerService
) : RentalsApi {

  @RequestMapping(
      value = ["/rentals/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun getRentalById(@PathVariable("id") id: Long): ResponseEntity<RentalDTO?> {
    val rental = rentalService.getRentalById(id)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(toDTO(rental))
  }

  @RequestMapping(
      value = ["/rentals/customer/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun getRentalsByCustomerId(@PathVariable("id") id: Long): ResponseEntity<List<RentalDTO>?> {
    val rentals = rentalService.getRentalsByCustomerId(id)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(rentals.map { toDTO(it) })
  }

  @RequestMapping(
      value = ["/rentals/staff/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.GET]
  ) override fun getRentalsByStaffId(@PathVariable("id") id: Long): ResponseEntity<List<RentalDTO>?> {
    val rentals = rentalService.getRentalsByStaffId(id)?: return ResponseEntity.ok().build()

    return ResponseEntity.ok(rentals.map { toDTO(it) })
  }

  @RequestMapping(
      value = ["/rentals"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.POST]
  ) override fun createRental(@RequestBody rentalDTO: RentalDTO): ResponseEntity<RentalDTO> {
    var rental = toEntity(rentalDTO)
    rental = rentalService.createRental(rental)

    val result = toDTO(rental)
    return ResponseEntity.ok(result)
  }

  @RequestMapping(
      value = ["/rentals/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.PUT]
  ) override fun replaceRental(
      @PathVariable("id") id: Long?,
      @RequestBody rentalDTO: RentalDTO
  ): ResponseEntity<RentalDTO> {
    if (id == null) throw BadRequestException("ID should not be null")
    var rental = toEntity(rentalDTO)
    rental = rentalService.updateRental(id, rental)

    val result = toDTO(rental)
    return ResponseEntity.ok(result)
  }

  @RequestMapping(
      value = ["/rentals/{id}"],
      produces = ["application/json"],
      consumes = ["application/json"],
      method = [RequestMethod.DELETE]
  ) override fun deleteRental(@PathVariable("id") id: Long?): ResponseEntity<Void> {
    if (id == null) throw BadRequestException("ID should not be null")
    rentalService.deleteRental(id)
    return ResponseEntity.ok().build()
  }

  private fun toDTO(rental: Rental): RentalDTO {
    val rentalDTO = RentalDTO()

    rentalDTO.id = rental.id
    rentalDTO.filmId = rental.filmId
    rentalDTO.storeId = rental.storeId
    rentalDTO.staffId = rental.staffId
    rentalDTO.rentalDate = rental.rentalDate
    rentalDTO.returnDate = rental.returnDate
    rentalDTO.customerId = rental.customer?.id
    rentalDTO.lastUpdate = rental.lastUpdate

    return rentalDTO
  }

  private fun toEntity(rentalDTO: RentalDTO): Rental {
    val rental = Rental()

    rental.id = rentalDTO.id
    rental.staffId = rentalDTO.staffId
    rental.filmId = rentalDTO.filmId
    rental.storeId = rentalDTO.storeId
    rental.returnDate = rentalDTO.returnDate
    rental.rentalDate = rentalDTO.rentalDate
    rental.lastUpdate = rentalDTO.lastUpdate

    if (rentalDTO.customerId != null) {
      val customerId = rentalDTO.customerId
      val customer = customerService.getCustomerById(customerId)?:
        throw NotFoundException("Customer for ID $customerId does not exist")

      rental.customer = customer
    }

    return rental
  }

}
