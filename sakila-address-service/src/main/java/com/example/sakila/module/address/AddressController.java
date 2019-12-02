package com.example.sakila.module.address;

import com.example.sakila.exception.BadRequestException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.AddressesApi;
import com.example.sakila.generated.server.model.AddressDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AddressController implements AddressesApi {

  private final AddressService addressService;

  @Autowired
  public AddressController(AddressService addressService) {
    this.addressService = addressService;
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

  private AddressDTO toDTO(Address address) {
    AddressDTO addressDTO = new AddressDTO();
    BeanUtils.copyProperties(address, addressDTO);
    addressDTO.setCityId(address.getCity().getId());
    addressDTO.setLastUpdate(OffsetDateTime.ofInstant(address.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return addressDTO;
  }
}
