package com.example.sakila.module.rental.repository

import com.example.sakila.module.rental.Rental

interface RentalRepository {

  fun getRentalById(id: Long): Rental?

  fun getRentalsByCustomerId(id: Long): List<Rental>?

  fun getRentalsByStaffId(id: Long): List<Rental>?

  fun insertRental(rental: Rental): Rental

  fun updateRental(rental: Rental): Rental

  fun deleteRental(rental: Rental)

}
