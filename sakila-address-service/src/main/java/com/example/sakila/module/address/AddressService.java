package com.example.sakila.module.address;

import com.example.sakila.module.address.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

  private AddressRepository addressRepository;

  @Autowired
  AddressService(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  Address getAddressById(Long id) {
    if (id == null) return null;
    return addressRepository.getAddressById(id);
  }

  List<Address> getAddressesByCity(Long cityId) {
    if (cityId == null) return null;
    return addressRepository.getAddressesByCity(cityId);
  }

  List<Address> getAddressesByCountry(Long countryId) {
    if (countryId == null) return null;
    return addressRepository.getAddressesByCountry(countryId);
  }
}
