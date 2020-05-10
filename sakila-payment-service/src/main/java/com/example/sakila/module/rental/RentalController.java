package com.example.sakila.module.rental;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.RentalsApi;
import com.example.sakila.generated.server.model.RentalDTO;
import com.example.sakila.module.customer.Customer;
import com.example.sakila.module.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RentalController implements RentalsApi {

  private final RentalService rentalService;

  private final CustomerService customerService;

  @Autowired
  public RentalController(RentalService rentalService, CustomerService customerService) {
    this.rentalService = rentalService;
    this.customerService = customerService;
  }

  @Override
  public ResponseEntity<RentalDTO> getRentalById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(rentalService.getRentalById(id)));
  }

  @Override
  public ResponseEntity<List<RentalDTO>> getRentalsByInventoryId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
      rentalService.getRentalsByInventoryId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<RentalDTO>> getRentalsByCustomerId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        rentalService.getRentalsByCustomerId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<RentalDTO>> getRentalsByStaffId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        rentalService.getRentalsByStaffId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
    return ResponseEntity.ok(toDTO(rentalService.createRental(toEntity(rentalDTO))));
  }

  @Override
  public ResponseEntity<RentalDTO> replaceRental(@PathVariable("id") Long id, @RequestBody RentalDTO rentalDTO) {
    return ResponseEntity.ok(toDTO(rentalService.updateRental(id, toEntity(rentalDTO))));
  }

  @Override
  public ResponseEntity<Void> deleteRental(@PathVariable("id") Long id) {
    rentalService.deleteRental(id);
    return ResponseEntity.ok(null);
  }

  private RentalDTO toDTO(Rental rental) {
    RentalDTO rentalDTO = new RentalDTO();
    rentalDTO.setId(rental.getId());
    rentalDTO.setFilmId(rental.getFilmId());
    rentalDTO.setStoreId(rental.getStoreId());
    rentalDTO.setStaffId(rental.getStaffId());
    if (rental.getRentalDate() != null) rentalDTO.setRentalDate(rental.getRentalDate());
    if (rental.getCustomer() != null) rentalDTO.setCustomerId(rental.getCustomer().getId());
    if (rental.getReturnDate() != null) rentalDTO.setReturnDate(rental.getReturnDate());
    if (rental.getLastUpdate() != null) rentalDTO.setLastUpdate(rental.getLastUpdate());
    return rentalDTO;
  }

  private Rental toEntity(RentalDTO rentalDTO) {
    Rental rental = new Rental();
    rental.setId(rentalDTO.getId());
    rental.setStaffId(rentalDTO.getStaffId());
    rental.setFilmId(rentalDTO.getFilmId());
    rental.setStoreId(rentalDTO.getStoreId());

    if (rentalDTO.getCustomerId() != null) {
      Customer customer = customerService.getCustomerById(rentalDTO.getCustomerId());
      if (customer == null) throw new NotFoundException(
          "Custmer for ID " + rentalDTO.getCustomerId() + " does not exist"
      );

      rental.setCustomer(customer);
    }

    if (rentalDTO.getRentalDate() != null) rental.setRentalDate(rentalDTO.getRentalDate());
    if (rentalDTO.getReturnDate() != null) rental.setReturnDate(rentalDTO.getReturnDate());
    if (rentalDTO.getLastUpdate() != null) rental.setLastUpdate(rentalDTO.getLastUpdate());

    return rental;
  }

  private OffsetDateTime toOffsetDateTime(Date date) {
    return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }
}
