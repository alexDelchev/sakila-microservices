package com.example.sakila.module.staff

import com.example.sakila.exception.NotFoundException
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

  void 'updateStaff - should throw NotFoundException'() {
    given:
    final long NON_EXISTING_STAFF_ID = -1L

    when:
    staffService.updateStaff(NON_EXISTING_STAFF_ID, new Staff())

    then:
    thrown NotFoundException
  }

  void 'deleteStaff - should throw NotFoundException'() {
    given:
    final long NON_EXISTING_STAFF_ID = -1L

    when:
    staffService.updateStaff(NON_EXISTING_STAFF_ID, new Staff())

    then:
    thrown NotFoundException
  }
}
