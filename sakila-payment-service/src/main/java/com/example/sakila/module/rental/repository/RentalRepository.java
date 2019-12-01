package com.example.sakila.module.rental.repository;

import com.example.sakila.module.rental.Rental;

import java.util.List;

public interface RentalRepository {

  Rental getRentalById(Long id);

  List<Rental> getRentalsByInventoryId(Long id);

  List<Rental> getRentalsByCustomerId(Long id);

  List<Rental> getRentalsByStaffId(Long id);

}
