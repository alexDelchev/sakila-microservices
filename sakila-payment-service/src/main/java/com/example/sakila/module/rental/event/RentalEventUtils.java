package com.example.sakila.module.rental.event;

import com.example.sakila.module.rental.Rental;
import com.example.sakila.module.rental.event.model.RentalEventDTO;

public class RentalEventUtils {

  public static RentalEventDTO toDTO(Rental rental) {
    RentalEventDTO dto = new RentalEventDTO();

    dto.setId(rental.getId());
    dto.setRentalDate(rental.getRentalDate());
    dto.setFilmId(rental.getFilmId());
    dto.setStoreId(rental.getStoreId());
    dto.setReturnDate(rental.getReturnDate());
    dto.setStaffId(rental.getStaffId());
    dto.setLastUpdate(rental.getLastUpdate());

    if (rental.getCustomer() != null) {
      dto.setCustomerId(rental.getCustomer().getId());
    } else {
      throw new IllegalArgumentException(String.format("Rental with id %d has no customer", rental.getId()));
    }

    return dto;
  }
}
