package com.example.sakila.module.rental;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.customer.Customer;
import com.example.sakila.module.rental.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

  @Mock
  private EventBus eventBus;

  @Mock
  private RentalRepository rentalRepository;

  @InjectMocks
  private RentalService rentalService;

  @Test
  void getRentalById() {
    Rental rental = rentalService.getRentalById(null);

    assertNull(rental);
  }

  @Test
  void getRentalsByCustomerId() {
    List<Rental> rentals = rentalService.getRentalsByCustomerId(null);

    assertNull(rentals);
  }

  @Test
  void getRentalsByStaffId() {
    List<Rental> rentals = rentalService.getRentalsByStaffId(null);

    assertNull(rentals);
  }

  @Test
  void updateRental() {
    final long existingRentalId = 1L;
    final Rental existingRental = rental(existingRentalId);
    when(rentalRepository.getRentalById(existingRentalId)).thenReturn(existingRental);

    final long nonExistingRentalId = -1L;
    when(rentalRepository.getRentalById(nonExistingRentalId)).thenReturn(null);

    when(rentalRepository.updateRental(existingRental)).thenReturn(existingRental);

    assertDoesNotThrow(() -> rentalService.updateRental(existingRentalId, existingRental));
    assertThrows(NotFoundException.class, () -> rentalService.updateRental(nonExistingRentalId, existingRental));
  }

  private Rental rental(long id) {
    Rental rental = new Rental(id);
    rental.setCustomer(new Customer(id));
    return rental;
  }

  @Test
  void deleteRental() {
    final long nonExistingRentalId = -1L;

    assertThrows(NotFoundException.class, () -> rentalService.deleteRental(nonExistingRentalId));
  }
}
