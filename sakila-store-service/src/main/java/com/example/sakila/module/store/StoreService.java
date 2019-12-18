package com.example.sakila.module.store;

import com.example.sakila.module.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

  private final StoreRepository storeRepository;

  @Autowired
  public StoreService(StoreRepository storeRepository) {
    this.storeRepository = storeRepository;
  }

  public Store getStoreById(Long id) {
    if (id == null) return null;
    return storeRepository.getStoreById(id);
  }

  public Store getStoreByIaddressId(Long addressId) {
    if (addressId == null) return null;
    return storeRepository.getStoreByAddressId(addressId);
  }
}
