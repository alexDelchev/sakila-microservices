package com.example.sakila.module.address;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
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

  private AddressRepository addressRepository;

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

  public Address createAddress(Address address) {
    return addressRepository.insertAddress(address);
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

    return addressRepository.updateAddress(target);
  }

  public void deleteAddress(Long id) {
    Address address = addressRepository.getAddressById(id);
    if (address == null) throw new NotFoundException("Address for ID " + id + " does not exist");

    try {
      addressRepository.deleteAddress(address);
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
