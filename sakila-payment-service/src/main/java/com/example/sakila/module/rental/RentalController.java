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

  private RentalDTO toDTO(Rental rental) {
    RentalDTO rentalDTO = new RentalDTO();
    rentalDTO.setId(rental.getId());
    rentalDTO.setRentalDate(toOffsetDateTime(rental.getRentalDate()));
    rentalDTO.setCustomerId(rental.getCustomer().getId());
    rentalDTO.setInventoryId(rental.getInventory_id());
    if (rental.getReturnDate() != null) rentalDTO.setReturnDate(toOffsetDateTime(rental.getReturnDate()));
    rentalDTO.setStaffId(rental.getStaff_id());
    rentalDTO.setLastUpdate(toOffsetDateTime(rental.getLastUpdate()));
    return rentalDTO;
  }

  private Rental toEntity(RentalDTO rentalDTO) {
    Rental rental = new Rental();
    rental.setId(rentalDTO.getId());
    rental.setStaff_id(rentalDTO.getStaffId());
    rental.setInventory_id(rentalDTO.getInventoryId());

    if (rentalDTO.getCustomerId() != null) {
      Customer customer = customerService.getCustomerById(rentalDTO.getCustomerId());
      if (customer == null) throw new NotFoundException(
          "Custmer for ID " + rentalDTO.getCustomerId() + " does not exist"
      );

      rental.setCustomer(customer);
    }

    if (rentalDTO.getRentalDate() != null) rental.setRentalDate(Date.from(rentalDTO.getRentalDate().toInstant()));
    if (rentalDTO.getReturnDate() != null) rental.setReturnDate(Date.from(rentalDTO.getReturnDate().toInstant()));
    if (rentalDTO.getLastUpdate() != null) rental.setLastUpdate(Date.from(rentalDTO.getLastUpdate().toInstant()));

    return rental;
  }

  private OffsetDateTime toOffsetDateTime(Date date) {
    return OffsetDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }
}
