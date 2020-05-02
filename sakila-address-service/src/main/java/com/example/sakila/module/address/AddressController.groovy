package com.example.sakila.module.address

import com.example.sakila.exception.NotFoundException
import com.example.sakila.generated.server.api.AddressesApi
import com.example.sakila.generated.server.model.AddressDTO
import com.example.sakila.module.city.City
import com.example.sakila.module.city.CityService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

import java.sql.Date
import java.time.OffsetDateTime
import java.time.ZoneId

@Controller
class AddressController implements AddressesApi {

  private final AddressService addressService

  private final CityService cityService

  @Autowired
  AddressController(AddressService addressService, CityService cityService) {
    this.addressService = addressService
    this.cityService = cityService
  }

  @Override
  ResponseEntity<AddressDTO> getAddressById(@PathVariable('id') Long id) {
    Address address = addressService.getAddressById(id)
    ResponseEntity.ok(toDTO(address))
  }

  @Override
  ResponseEntity<List<AddressDTO>> getAddressesByCityId(@PathVariable('id') Long id) {
    List<Address> addresses = addressService.getAddressesByCity(id)
    ResponseEntity.ok(addresses.collect { toDTO(it) })
  }

  @Override
  ResponseEntity<List<AddressDTO>> getAddressesByCountryId(@PathVariable('id') Long id) {
    List<Address> addresses = addressService.getAddressesByCountry(id)
    ResponseEntity.ok(addresses.collect { toDTO(it) })
  }

  @Override
  ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
    ResponseEntity.ok(toDTO(addressService.createAddress(toEntity(addressDTO))))
  }

  @Override
  ResponseEntity<AddressDTO> replaceAddress(@PathVariable('id') Long id, @RequestBody AddressDTO addressDTO) {
    ResponseEntity.ok(toDTO(addressService.updateAddress(id, toEntity(addressDTO))))
  }

  @Override
   ResponseEntity<Void> deleteAddress(@PathVariable('id') Long id) {
    addressService.deleteAddress(id)
    ResponseEntity.ok(null)
  }

  private AddressDTO toDTO(Address address) {
    AddressDTO addressDTO = new AddressDTO()
    BeanUtils.copyProperties(address, addressDTO)
    addressDTO.cityId = address.city.id

    if (address.lastUpdate) {
      addressDTO.lastUpdate = OffsetDateTime.ofInstant(address.lastUpdate.toInstant(), ZoneId.systemDefault())
    }

    addressDTO
  }

  private Address toEntity(AddressDTO addressDTO) {
    Address address = new Address()
    BeanUtils.copyProperties(addressDTO, address)

    if (addressDTO.cityId != null) {
      City city = cityService.getCityById(addressDTO.cityId)
      if (!city) throw new NotFoundException("City for ID ${addressDTO.cityId} does not exist")
      address.city = city
    }

    if (addressDTO.lastUpdate) address.setLastUpdate(Date.from(addressDTO.lastUpdate.toInstant()))

    address
  }
}
