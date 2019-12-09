package com.example.sakila.module.address;

import com.example.sakila.module.address.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

  @Mock
  private AddressRepository addressRepository;

  @InjectMocks
  private AddressService addressService;

  @Test
  void getAddressById() {
    Address address = addressService.getAddressById(null);

    assertNull(address);
  }

  @Test
  void getAddressesByCity() {
    List<Address> addresses = addressService.getAddressesByCity(null);

    assertNull(addresses);
  }

  @Test
  void getAddressesByCountry() {
    List<Address> addresses = addressService.getAddressesByCountry(null);

    assertNull(addresses);
  }
}
