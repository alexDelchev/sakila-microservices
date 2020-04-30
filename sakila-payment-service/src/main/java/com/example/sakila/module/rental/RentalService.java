package com.example.sakila.module.rental;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.rental.event.RentalEventUtils;
import com.example.sakila.module.rental.event.model.RentalCreatedEvent;
import com.example.sakila.module.rental.event.model.RentalDeletedEvent;
import com.example.sakila.module.rental.event.model.RentalEventDTO;
import com.example.sakila.module.rental.event.model.RentalUpdatedEvent;
import com.example.sakila.module.rental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

  private final EventBus eventBus;

  private final RentalRepository rentalRepository;

  @Autowired
  public RentalService(@Qualifier("RentalEventBus") EventBus eventBus, RentalRepository rentalRepository) {
    this.eventBus = eventBus;
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

  private void generateCreatedEvent(Rental rental) {
    RentalEventDTO dto = RentalEventUtils.toDTO(rental);
    RentalCreatedEvent createdEvent = new RentalCreatedEvent();
    createdEvent.setDto(dto);
    eventBus.emit(createdEvent);
  }

  public Rental createRental(Rental rental) {
    Rental result = rentalRepository.insertRental(rental);

    generateCreatedEvent(result);

    return result;
  }

  public void generateUpdatedEvent(Rental rental) {
    RentalEventDTO dto = RentalEventUtils.toDTO(rental);
    RentalUpdatedEvent updatedEvent = new RentalUpdatedEvent();
    updatedEvent.setDto(dto);
    eventBus.emit(updatedEvent);
  }

  public Rental updateRental(Long id, Rental source) {
    Rental target = getRentalById(id);
    if (target == null) throw new NotFoundException("Rental for ID " + id + " does not exist");

    target.setRentalDate(source.getRentalDate());
    target.setReturnDate(source.getReturnDate());
    target.setCustomer(source.getCustomer());
    target.setFilmId(source.getFilmId());
    target.setStoreId(source.getStoreId());
    target.setStaffId(source.getStaffId());

    Rental result = rentalRepository.updateRental(target);

    generateUpdatedEvent(result);

    return result;
  }

  public void generateDeletedEvent(Long id) {
    RentalDeletedEvent deletedEvent = new RentalDeletedEvent();
    deletedEvent.setRentalId(id);
    eventBus.emit(deletedEvent);
  }

  public void deleteRental(Long id) {
    Rental rental = getRentalById(id);
    if (rental == null) throw new NotFoundException("Rental for ID " + id + " does not exist");

    try {
      rentalRepository.deleteRental(rental);
      generateDeletedEvent(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
