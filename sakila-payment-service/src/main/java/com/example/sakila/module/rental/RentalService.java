package com.example.sakila.module.rental;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.rental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

  public Rental createRental(Rental rental) {
    return rentalRepository.insertRental(rental);
  }

  public Rental updateRental(Long id, Rental source) {
    Rental target = getRentalById(id);
    if (target == null) throw new NotFoundException("Rental for ID " + id + " does not exist");

    target.setRentalDate(source.getRentalDate());
    target.setReturnDate(source.getReturnDate());
    target.setCustomer(source.getCustomer());
    target.setInventory_id(source.getInventory_id());
    target.setStaff_id(source.getStaff_id());

    return rentalRepository.updateRental(target);
  }

  public void deleteRental(Long id) {
    Rental rental = getRentalById(id);
    if (rental == null) throw new NotFoundException("Rental for ID " + id + " does not exist");

    try {
      rentalRepository.deleteRental(rental);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
