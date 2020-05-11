package com.example.sakila.module.rental.event

import com.example.sakila.module.rental.Rental
import com.example.sakila.module.rental.event.model.RentalEventDTO

class RentalEventUtils {

  companion object {

    @JvmStatic
    fun toDTO(rental: Rental): RentalEventDTO {
      val dto = RentalEventDTO()

      dto.id = rental.id
      dto.rentalDate = rental.rentalDate
      dto.filmId = rental.filmId
      dto.storeId = rental.storeId
      dto.returnDate = rental.returnDate
      dto.staffId = rental.staffId
      dto.lastUpdate = rental.lastUpdate

      dto.customerId = rental.customer?.id?:
          throw IllegalArgumentException("Rental with ID ${rental.id} has not customer")

      return dto
    }

  }
}
