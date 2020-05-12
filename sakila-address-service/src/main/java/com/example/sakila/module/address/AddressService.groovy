package com.example.sakila.module.address

import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.DataConflictException
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.address.event.AddressEventUtils
import com.example.sakila.module.address.event.model.AddressCreatedEvent
import com.example.sakila.module.address.event.model.AddressDeletedEvent
import com.example.sakila.module.address.event.model.AddressEventDTO
import com.example.sakila.module.address.event.model.AddressUpdatedEvent
import com.example.sakila.module.address.repository.AddressRepository
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Slf4j
@Service
class AddressService {

  private final EventBus eventBus

  private final AddressRepository addressRepository

  @Autowired
  AddressService(@Qualifier('AddressEventBus') EventBus eventBus,  AddressRepository addressRepository) {
    this.eventBus = eventBus
    this.addressRepository = addressRepository
  }

  Address getAddressById(Long id) {
    if (!id) return null
    addressRepository.getAddressById(id)
  }

  List<Address> getAddressesByCity(Long cityId) {
    if (!cityId) return null
    addressRepository.getAddressesByCity(cityId)
  }

  List<Address> getAddressesByCountry(Long countryId) {
    if (!countryId) return null
    addressRepository.getAddressesByCountry(countryId)
  }

  private void generateCreatedEvent(Address address) {
    AddressEventDTO dto = AddressEventUtils.toDto(address)
    AddressCreatedEvent event = new AddressCreatedEvent(
        dto: dto
    )

    eventBus.emit(event)
  }

  Address createAddress(Address address) {
    log.info('Creating Address')
    Address result = addressRepository.insertAddress(address)
    log.info("Created Address id: ${result.id}")

    generateCreatedEvent(result)

    result
  }

  private void generateUpdatedEvent(Address address) {
    AddressEventDTO dto = AddressEventUtils.toDto(address)
    AddressUpdatedEvent event = new AddressUpdatedEvent(
        dto: dto
    )
    eventBus.emit(event)
  }

  Address updateAddress(Long id, Address source) {
    Address target = addressRepository.getAddressById(id)
    if (!target) throw new NotFoundException('Target address for update does not exist')
    log.info("Updating Address (ID: ${id})")

    target.address = source.address
    target.address2 = source.address2
    target.district = source.district
    target.city = source.city
    target.postalCode = source.postalCode
    target.phone = source.phone

    Address result = addressRepository.updateAddress(target)

    generateUpdatedEvent(result)

    result
  }

  private void generateDeletedEvent(Long id) {
    AddressDeletedEvent event = new AddressDeletedEvent(
        id: id
    )

    eventBus.emit(event)
  }

  void deleteAddress(Long id) {
    Address address = addressRepository.getAddressById(id)
    if (!address) throw new NotFoundException("Address for ID ${id} does not exist")
    log.info("Deleting Address (ID: ${id})")

    try {
      addressRepository.deleteAddress(address)
      generateDeletedEvent(id)
    } catch (DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e)
    }
  }
}
