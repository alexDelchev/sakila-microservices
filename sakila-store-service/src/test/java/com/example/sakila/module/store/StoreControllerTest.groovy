package com.example.sakila.module.store

import com.example.sakila.exception.NotFoundException
import com.example.sakila.generated.server.model.StoreDTO
import com.example.sakila.module.staff.StaffService
import spock.lang.Specification

class StoreControllerTest extends Specification {

  private StaffService staffService = Mock(StaffService)

  private StoreService storeService = Mock(StoreService)

  private StoreController storeController = new StoreController(storeService, staffService)

  void 'createStore - should throw NotFoundException'() {
    given:
    final long NON_EXISTING_STAFF_ID = -1
    StoreDTO storeDTO = new StoreDTO()
    storeDTO.setManagerStaffId(NON_EXISTING_STAFF_ID)

    when:
    storeController.createStore(storeDTO)

    then:
    thrown NotFoundException
  }

  void 'replaceStore - should throw NotFoundException' () {
    given:
    final long NON_EXISTING_STAFF_ID = -1
    StoreDTO storeDTO = new StoreDTO()
    storeDTO.setManagerStaffId(NON_EXISTING_STAFF_ID)

    when:
    storeController.replaceStore(1L, storeDTO)

    then:
    thrown NotFoundException
  }
}
