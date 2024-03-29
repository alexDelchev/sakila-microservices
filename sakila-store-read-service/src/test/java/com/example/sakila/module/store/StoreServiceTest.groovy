package com.example.sakila.module.store

import com.example.sakila.module.store.repository.StoreRepository
import spock.lang.Specification

class StoreServiceTest extends Specification {

  private StoreRepository storeRepository = Mock(StoreRepository.class)

  private StoreService storeService = new StoreService(storeRepository)

  void 'getStoreById - should return null'() {
    given:
    Store store

    when:
    store = storeService.getStoreById(null)

    then:
    store == null
  }

  void 'getStoreByAddressId - should return null'() {
    given:
    Store store

    when:
    store = storeService.getStoreByAddressId(null)

    then:
    store == null
  }

  void 'getStoreByManagerStaffId - should return null'() {
    given:
    Store store

    when:
    store = storeService.getStoreByManagerStaffId(null)

    then:
    store == null
  }
}
