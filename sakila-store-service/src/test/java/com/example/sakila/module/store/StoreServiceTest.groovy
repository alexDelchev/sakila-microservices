package com.example.sakila.module.store

import com.example.sakila.exception.DataConflictException
import com.example.sakila.module.staff.Staff
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

  void 'createStore - should throw DataConflictException when managerStaffId is not unique'() {
    given:
    final long EXISTING_STAFF_ID = 1L
    Staff managerStaff = new Staff()
    managerStaff.setId(EXISTING_STAFF_ID)

    Store store = new Store()
    store.setManagerStaff(managerStaff)

    final long EXISTING_OTHER_STAFF_ID = 2L
    Staff otherStaff = new Staff()
    otherStaff.setId(EXISTING_OTHER_STAFF_ID)

    Store otherStore = new Store()
    otherStore.setManagerStaff(otherStaff)

    storeRepository.getStoreByManagerStaffId(_ as Long) >> new Store()
    storeRepository.getStoreById(_ as Long) >> store

    when:
    storeService.createStore(otherStore)

    then:
    thrown DataConflictException
  }

  void 'updateStore - should throw DataConflictException'() {
    given:
    final long EXISTING_STAFF_ID = 1L
    Staff managerStaff = new Staff()
    managerStaff.setId(EXISTING_STAFF_ID)

    final long STORE_ID = 1L
    Store store = new Store()
    store.setId(STORE_ID)
    store.setManagerStaff(managerStaff)

    final long EXISTING_OTHER_STAFF_ID = 2L
    Staff otherStaff = new Staff()
    otherStaff.setId(EXISTING_OTHER_STAFF_ID)

    final long OTHER_STORE_ID = 2L
    Store otherStore = new Store()
    otherStore.setId(OTHER_STORE_ID)
    otherStore.setManagerStaff(otherStaff)

    storeRepository.getStoreByManagerStaffId(EXISTING_OTHER_STAFF_ID) >> otherStore
    storeRepository.getStoreById(STORE_ID) >> store

    when:
    storeService.updateStore(STORE_ID, otherStore)

    then:
    thrown DataConflictException
  }
}
