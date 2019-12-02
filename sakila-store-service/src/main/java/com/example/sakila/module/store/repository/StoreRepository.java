package com.example.sakila.module.store.repository;

import com.example.sakila.module.store.Store;

public interface StoreRepository {

  Store getStoreById(Long id);

  Store getStoreByAddressId(Long addressId);
}
