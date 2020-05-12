package com.example.sakila.module.store;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.store.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

  private final Logger log = LoggerFactory.getLogger(StoreService.class);

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
        String.format("Staff with ID %d is already a manager", store.getManagerStaffId())
    );

    log.info("Creating Store (ID: {})", store.getId());
    return storeRepository.insertStore(store);
  }

  public Store updateStore(Long id, Store source) {
    Store target = getStoreById(id);
    if (target == null) throw new NotFoundException(String.format("Store for ID %d does not exist", id));

    if (!target.getManagerStaffId().equals(source.getManagerStaffId()) &&
        getStoreByManagerStaffId(source.getManagerStaffId()) != null)
      throw new DataConflictException(String.format("Staff with ID %d is already a manager", source.getManagerStaffId()));

    log.info("Updating Store (ID: {})", id);

    target.setAddressId(source.getAddressId());
    target.setManagerStaffId(source.getManagerStaffId());

    return storeRepository.updateStore(target);
  }

  public void deleteStore(Long id) {
    Store store = getStoreById(id);
    if (store == null) throw new NotFoundException("Store for ID " + id + " does not exist");
    log.info("Deleting Store (ID: {})", id);

    try {
      storeRepository.deleteStore(store);
    } catch(DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
