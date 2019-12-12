package com.example.sakila.module.address;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.address.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

  @Test
  void updateAddress() {
    final long EXISTING_ID = 1L;
    when(addressRepository.getAddressById(EXISTING_ID)).thenReturn(new Address());

    final long NON_EXISTING_ID = -1L;
    when(addressRepository.getAddressById(NON_EXISTING_ID)).thenReturn(null);

    when(addressRepository.updateAddress(any(Address.class))).thenReturn(new Address());

    assertDoesNotThrow(() -> addressService.updateAddress(EXISTING_ID, new Address()));
    assertThrows(NotFoundException.class, () -> addressService.updateAddress(NON_EXISTING_ID, new Address()));
  }

  @Test
  void deleteAddress() {
    final long NON_EXISTING_ADDRESS_ID = -1L;

    assertThrows(NotFoundException.class, () -> addressService.deleteAddress(NON_EXISTING_ADDRESS_ID));
  }
}
