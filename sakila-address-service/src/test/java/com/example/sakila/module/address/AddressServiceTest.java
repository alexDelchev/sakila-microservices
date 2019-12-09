package com.example.sakila.module.address;

import com.example.sakila.module.address.repository.AddressRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

  @Mock
  private AddressRepository addressRepository;

  @InjectMocks
  private AddressService addressService;

}
