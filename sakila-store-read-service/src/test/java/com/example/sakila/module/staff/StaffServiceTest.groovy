package com.example.sakila.module.staff

import com.example.sakila.module.staff.repository.StaffRepository
import spock.lang.Specification

class StaffServiceTest extends Specification {

  private StaffRepository staffRepository = Mock(StaffRepository.class)

  private StaffService staffService = new StaffService(staffRepository)

  void 'getStaffById - should return null'() {
    given:
    Staff staff

    when:
    staff = staffService.getStaffById(null)

    then:
    staff == null
  }

  void 'getStaffByStoreId - should return null'() {
    given:
    List<Staff> staff

    when:
    staff = staffService.getStaffByStoreId(null)

    then:
    staff == null
  }
}
