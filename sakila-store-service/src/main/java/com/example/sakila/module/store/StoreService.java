package com.example.sakila.module.store;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
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

  public Store updateStore(Long id, Store source) {
    Store target = getStoreById(id);
    if (target == null) throw new NotFoundException("Store for ID " + id + " does not exist");

    if (!target.getManagerStaff().getId().equals(source.getManagerStaff().getId()) &&
        getStoreByManagerStaffId(source.getManagerStaff().getId()) != null)
      throw new DataConflictException("Staff with ID " + source.getManagerStaff().getId() + " is already a manager");

    target.setAddress_id(source.getAddress_id());
    target.setManagerStaff(source.getManagerStaff());

    return storeRepository.updateStore(target);
  }
}
