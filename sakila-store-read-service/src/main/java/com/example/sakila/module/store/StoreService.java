package com.example.sakila.module.store;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.store.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    if (getStoreByManagerStaffId(store.getManagerStaffId()) != null) throw new DataConflictException(
        "Staff with ID " + store.getManagerStaffId() + " is already a manager"
    );

    return storeRepository.insertStore(store);
  }

  public Store updateStore(Long id, Store source) {
    Store target = getStoreById(id);
    if (target == null) throw new NotFoundException("Store for ID " + id + " does not exist");

    if (!target.getManagerStaffId().equals(source.getManagerStaffId()) &&
        getStoreByManagerStaffId(source.getManagerStaffId()) != null)
      throw new DataConflictException("Staff with ID " + source.getManagerStaffId() + " is already a manager");

    target.setAddressId(source.getAddressId());
    target.setManagerStaffId(source.getManagerStaffId());

    return storeRepository.updateStore(target);
  }

  public void deleteStore(Long id) {
    Store store = getStoreById(id);
    if (store == null) throw new NotFoundException("Store for ID " + id + " does not exist");

    try {
      storeRepository.deleteStore(store);
    } catch(DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
