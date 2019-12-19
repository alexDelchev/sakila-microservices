package com.example.sakila.module.staff

import com.example.sakila.module.store.StoreService
import spock.lang.Specification

class StaffControllerTest extends Specification {

  private StoreService storeService = Mock(StoreService)

  private StaffService staffService = Mock(StaffService)

  private StaffController staffController = new StaffController(staffService, storeService)
  
}
