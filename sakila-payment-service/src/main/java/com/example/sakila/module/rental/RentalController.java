package com.example.sakila.module.rental;

import com.example.sakila.generated.server.api.RentalsApi;
import com.example.sakila.generated.server.model.RentalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class RentalController implements RentalsApi {

  private final RentalService rentalService;

  @Autowired
  public RentalController(RentalService rentalService) {
    this.rentalService = rentalService;
  }

  @Override
  public ResponseEntity<RentalDTO> getRentalById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(rentalService.getRentalById(id)));
  }

  private RentalDTO toDTO(Rental rental) {
    RentalDTO rentalDTO = new RentalDTO();
    rentalDTO.setId(rental.getId());
    rentalDTO.setRentalDate(toOffsetDateTime(rental.getRentalDate()));
    rentalDTO.setCustomerId(rental.getCustomer().getId());
    rentalDTO.setInventoryId(rental.getInventory_id());
    rentalDTO.setReturnDate(toOffsetDateTime(rental.getReturnDate()));
    rentalDTO.setStaffId(rental.getStaff_id());
    rentalDTO.setLastUpdate(toOffsetDateTime(rental.getLastUpdate()));
    return rentalDTO;
  }

  private OffsetDateTime toOffsetDateTime(Date date) {
    return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }
}
