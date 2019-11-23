package com.example.sakila.module.address;

import com.example.sakila.generated.server.api.AddressApi;
import com.example.sakila.generated.server.model.AddressDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AddressController implements AddressApi {

  private final AddressService addressService;

  @Autowired
  public AddressController(AddressService addressService) {
    this.addressService = addressService;
  }

  @Override
  public ResponseEntity<AddressDTO> getAddressById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(addressService.getAddressById(id)));
  }

  @Override
  public ResponseEntity<List<AddressDTO>> getAddressesByCityId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        addressService.getAddressesByCity(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<List<AddressDTO>> getAddressesByCountryId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        addressService.getAddressesByCountry(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  private AddressDTO toDTO(Address address) {
    AddressDTO addressDTO = new AddressDTO();
    BeanUtils.copyProperties(address, addressDTO);
    addressDTO.setCityId(address.getCity().getId());
    addressDTO.setLastUpdate(OffsetDateTime.ofInstant(address.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return addressDTO;
  }
}
