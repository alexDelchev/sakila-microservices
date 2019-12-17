package com.example.sakila.module.rental;

import com.example.sakila.module.rental.repository.RentalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}
