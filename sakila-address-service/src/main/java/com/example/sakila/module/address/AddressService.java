package com.example.sakila.module.address;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.address.event.AddressEventEmitter;
import com.example.sakila.module.address.event.AddressEventUtils;
import com.example.sakila.module.address.event.model.AddressCreatedEvent;
import com.example.sakila.module.address.event.model.AddressDeletedEvent;
import com.example.sakila.module.address.event.model.AddressEventDTO;
import com.example.sakila.module.address.event.model.AddressUpdatedEvent;
import com.example.sakila.module.address.repository.AddressRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class AddressService {

  private final EventBus eventBus;

  private final AddressRepository addressRepository;

  @Autowired
  AddressService(@Qualifier("AddressEventService") EventBus eventBus,  AddressRepository addressRepository) {
    this.eventBus = eventBus;
    this.addressRepository = addressRepository;
  }

  public Address getAddressById(Long id) {
    if (id == null) return null;
    return addressRepository.getAddressById(id);
  }

  public List<Address> getAddressesByCity(Long cityId) {
    if (cityId == null) return null;
    return addressRepository.getAddressesByCity(cityId);
  }

  public List<Address> getAddressesByCountry(Long countryId) {
    if (countryId == null) return null;
    return addressRepository.getAddressesByCountry(countryId);
  }

  private void generateCreatedEvent(Address address) {
    AddressEventDTO dto = AddressEventUtils.toDto(address);
    AddressCreatedEvent event = new AddressCreatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  public Address createAddress(Address address) {
    Address result = addressRepository.insertAddress(address);

    generateCreatedEvent(result);

    return result;
  }

  private void generateUpdatedEvent(Address address) {
    AddressEventDTO dto = AddressEventUtils.toDto(address);
    AddressUpdatedEvent event = new AddressUpdatedEvent();
    event.setDto(dto);
    eventBus.emit(event);
  }

  public Address updateAddress(Long id, Address source) {
    Address target = addressRepository.getAddressById(id);
    if (target == null) throw new NotFoundException("Target address for update does not exist");

    target.setAddress(source.getAddress());
    target.setAddress2(source.getAddress2());
    target.setDistrict(source.getDistrict());
    target.setCity(source.getCity());
    target.setPostalCode(source.getPostalCode());
    target.setPhone(source.getPhone());

    Address result = addressRepository.updateAddress(target);

    generateUpdatedEvent(result);

    return result;
  }

  private void generateDeletedEvent(Long id) {
    AddressDeletedEvent event = new AddressDeletedEvent();
    event.setId(id);
    eventBus.emit(event);
  }

  public void deleteAddress(Long id) {
    Address address = addressRepository.getAddressById(id);
    if (address == null) throw new NotFoundException("Address for ID " + id + " does not exist");

    try {
      addressRepository.deleteAddress(address);
      generateDeletedEvent(id);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
