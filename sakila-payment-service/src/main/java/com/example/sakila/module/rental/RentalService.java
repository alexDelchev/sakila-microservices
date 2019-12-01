package com.example.sakila.module.rental;

import com.example.sakila.module.rental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

  private final RentalRepository rentalRepository;

  @Autowired
  public RentalService(RentalRepository rentalRepository) {
    this.rentalRepository = rentalRepository;
  }

  public Rental getRentalById(Long id) {
    if (id == null) return null;
    return rentalRepository.getRentalById(id);
  }

  public List<Rental> getRentalsByInventoryId(Long id) {
    if (id == null) return null;
    return rentalRepository.getRentalsByInventoryId(id);
  }

  public List<Rental> getRentalsByCustomerId(Long id) {
    if (id == null) return null;
    return rentalRepository.getRentalsByCustomerId(id);
  }

  public List<Rental> getRentalsByStaffId(Long id) {
    if (id == null) return null;
    return rentalRepository.getRentalsByStaffId(id);
  }
}
