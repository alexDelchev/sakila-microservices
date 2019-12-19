package com.example.sakila.module.staff

import com.example.sakila.exception.NotFoundException
import com.example.sakila.generated.server.model.StaffDTO
import com.example.sakila.module.store.StoreService
import spock.lang.Specification

class StaffControllerTest extends Specification {

  private StoreService storeService = Mock(StoreService)

  private StaffService staffService = Mock(StaffService)

  private StaffController staffController = new StaffController(staffService, storeService)

  void 'createStaff - should throw NotFoundException when DTO references non existing Store'() {
    given:
    final long NON_EXISTING_STORE_ID = -1L
    StaffDTO staffDTO = new StaffDTO()
    staffDTO.setStoreId(NON_EXISTING_STORE_ID)

    when:
    staffController.createStaff(staffDTO)

    then:
    thrown NotFoundException
  }
}
