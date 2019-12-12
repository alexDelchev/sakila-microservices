package com.example.sakila.module.address;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.AddressesApi;
import com.example.sakila.generated.server.model.AddressDTO;
import com.example.sakila.module.city.City;
import com.example.sakila.module.city.CityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AddressController implements AddressesApi {

  private final AddressService addressService;

  private final CityService cityService;

  @Autowired
  public AddressController(AddressService addressService, CityService cityService) {
    this.addressService = addressService;
    this.cityService = cityService;
  }

  @Override
  public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") Long id) {
    Address address = addressService.getAddressById(id);
    if (address == null) throw new NotFoundException("Address with ID " + id + " does not exist");

    return ResponseEntity.ok(toDTO(address));
  }

  @Override
  public ResponseEntity<List<AddressDTO>> getAddressesByCityId(@PathVariable("id") Long id) {
    List<Address> addresses = addressService.getAddressesByCity(id);
    if (addresses == null || addresses.size() == 0) throw new NotFoundException(
        "Addresses for City ID " + id + " do not exist"
    );

    return ResponseEntity.ok(addresses.stream().map(this::toDTO).collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<List<AddressDTO>> getAddressesByCountryId(@PathVariable("id") Long id) {
    List<Address> addresses = addressService.getAddressesByCountry(id);
    if (addresses == null || addresses.size() == 0) throw new NotFoundException(
        "Addresses for Country ID " + id + " do not exist"
    );

    return ResponseEntity.ok(addresses.stream().map(this::toDTO).collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<AddressDTO> addNewAddress(@RequestBody AddressDTO addressDTO) {
    return ResponseEntity.ok(toDTO(addressService.addNewAddress(toEntity(addressDTO))));
  }

  @Override
  public ResponseEntity<AddressDTO> replaceAddress(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO) {
    return ResponseEntity.ok(toDTO(addressService.updateAddress(id, toEntity(addressDTO))));
  }

  @Override
  public  ResponseEntity<Void> deleteAddress(@PathVariable("id") Long id) {
    addressService.deleteAddress(id);
    return ResponseEntity.ok(null);
  }

  private AddressDTO toDTO(Address address) {
    AddressDTO addressDTO = new AddressDTO();
    BeanUtils.copyProperties(address, addressDTO);
    addressDTO.setCityId(address.getCity().getId());
    if (address.getLastUpdate() != null) {
      addressDTO.setLastUpdate(OffsetDateTime.ofInstant(address.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    }
    return addressDTO;
  }

  private Address toEntity(AddressDTO addressDTO) {
    Address address = new Address();
    BeanUtils.copyProperties(addressDTO, address);

    if (addressDTO.getCityId() != null) {
      City city = cityService.getCityById(addressDTO.getCityId());
      if (city == null) throw new NotFoundException("City for ID " + addressDTO.getCityId() + " does not exist");
      address.setCity(city);
    }

    if (addressDTO.getLastUpdate() != null) address.setLastUpdate(Date.from(addressDTO.getLastUpdate().toInstant()));
    return address;
  }
}
