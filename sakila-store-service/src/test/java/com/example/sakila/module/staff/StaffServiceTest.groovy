package com.example.sakila.module.staff

import com.example.sakila.module.staff.repository.StaffRepository
import spock.lang.Specification

class StaffServiceTest extends Specification {

  private StaffRepository staffRepository = Mock(StaffRepository.class)

  private StaffService staffService = new StaffService(staffRepository)

}
