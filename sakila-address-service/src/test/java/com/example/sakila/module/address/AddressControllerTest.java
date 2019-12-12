package com.example.sakila.module.address;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.model.AddressDTO;
import com.example.sakila.module.city.CityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

  @Mock
  private CityService cityService;

  @Mock
  private AddressService addressService;

  @InjectMocks
  private AddressController addressController;

  @Test
  void addNewAddress() {
    final long NON_EXISTING_CITY_ID = -1L;
    AddressDTO invalidDTO = new AddressDTO();
    invalidDTO.setCityId(NON_EXISTING_CITY_ID);

    assertThrows(NotFoundException.class, () -> addressController.addNewAddress(invalidDTO));
  }

}
