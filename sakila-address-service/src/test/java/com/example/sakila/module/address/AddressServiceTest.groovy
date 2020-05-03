package com.example.sakila.module.address

import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.address.repository.AddressRepository
import spock.lang.Specification

class AddressServiceTest extends Specification {

  private final EventBus eventBus = new EventBus('address-test-event-bus')

  private final AddressRepository addressRepository = Mock(AddressRepository)

  private final AddressService addressService = new AddressService(eventBus, addressRepository)

  void 'getAddressById - should return null when given id is null'() {
    when:
    Address address = addressService.getAddressById(null)

    then:
    address == null
  }

  void 'getAddressesByCity - should return null when given id is null'() {
    when:
    List<Address> addresses = addressService.getAddressesByCity(null)

    then:
    addresses == null
  }

  void 'getAddressesByCountry - should return null when given id is null'() {
    when:
    List<Address> addresses = addressService.getAddressesByCountry(null)

    then:
    addresses == null
  }

  void 'updateAddress - should not throw exception'() {
    given:
    final long existingId = 1L
    addressRepository.getAddressById(existingId) >> new Address()
    addressRepository.updateAddress(_) >> new Address()

    when:
    addressService.updateAddress(existingId, new Address())

    then:
    noExceptionThrown()
  }

  void 'updateAddress - should throw NotFoundException when there is no entity for the given id'() {
    given:
    final long nonExistingId = -1L

    when:
    addressService.updateAddress(nonExistingId, new Address())

    then:
    thrown NotFoundException
  }

  void 'deleteAddress - should throw NotFoundException when there is no entity for the given id'() {
    given:
    final long nonExistingId = -1L

    when:
    addressService.deleteAddress(nonExistingId)

    then:
    thrown NotFoundException
  }
}
