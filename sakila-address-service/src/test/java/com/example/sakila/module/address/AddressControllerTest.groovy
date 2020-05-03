package com.example.sakila.module.address

import com.example.sakila.exception.NotFoundException
import com.example.sakila.generated.server.model.AddressDTO
import com.example.sakila.module.city.CityService
import spock.lang.Specification

class AddressControllerTest extends Specification {

  private final CityService cityService = Mock(CityService.class)

  private final AddressService addressService = Mock(AddressService.class)

  private final AddressController addressController = new AddressController(addressService, cityService)

  void 'createAddress - should throw NotFoundException'() {
    given:
    final long NON_EXISTING_CITY_ID = -1L
    AddressDTO invalidDTO = new AddressDTO(cityId: NON_EXISTING_CITY_ID)

    when:
    addressController.createAddress(invalidDTO)

    then:
    thrown NotFoundException
  }

  void 'replaceAddress - should throw NotFoundException'() {
    given:
    final long UPDATED_ADDRESS_ID = 1L
    final long NON_EXISTING_CITY_ID = -1L
    AddressDTO invalidDTO = new AddressDTO(cityId: NON_EXISTING_CITY_ID)

    when:
    addressController.replaceAddress(UPDATED_ADDRESS_ID, invalidDTO)

    then:
    thrown NotFoundException
  }
}
