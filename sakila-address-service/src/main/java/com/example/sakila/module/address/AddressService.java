package com.example.sakila.module.address;

import com.example.sakila.exception.NotFoundException;
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

  public Address addNewAddress(Address address) {
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
}
