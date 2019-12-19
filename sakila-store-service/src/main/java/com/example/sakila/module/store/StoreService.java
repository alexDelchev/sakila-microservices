package com.example.sakila.module.store;

import com.example.sakila.exception.DataConflictException;
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

  public Store getStoreByAddressId(Long addressId) {
    if (addressId == null) return null;
    return storeRepository.getStoreByAddressId(addressId);
  }

  public Store getStoreByManagerStaffId(Long managerId) {
    if (managerId == null) return null;
    return storeRepository.getStoreByManagerStaffId(managerId);
  }

  public Store createStore(Store store) {
    if (getStoreByManagerStaffId(store.getManagerStaff().getId()) != null) throw new DataConflictException(
        "Staff with ID " + store.getManagerStaff().getId() + " is already a manager"
    );

    return storeRepository.insertStore(store);
  }
}
