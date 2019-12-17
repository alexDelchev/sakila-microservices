package com.example.sakila.module.rental;

import com.example.sakila.exception.NotFoundException;
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
  void getRentalByInventoryId() {
    List<Rental> rentals = rentalService.getRentalsByInventoryId(null);

    assertNull(rentals);
  }

  @Test
  void updateRental() {
    final long EXISITING_RENTAL_ID = 1L;
    when(rentalRepository.getRentalById(EXISITING_RENTAL_ID)).thenReturn(new Rental());

    final long NON_EXISTING_RENTAL_ID = -1L;
    when(rentalRepository.getRentalById(NON_EXISTING_RENTAL_ID)).thenReturn(null);

    when(rentalRepository.updateRental(any(Rental.class))).thenReturn(new Rental());

    assertDoesNotThrow(() -> rentalService.updateRental(EXISITING_RENTAL_ID, new Rental()));
    assertThrows(NotFoundException.class, () -> rentalService.updateRental(NON_EXISTING_RENTAL_ID, new Rental()));
  }
}
