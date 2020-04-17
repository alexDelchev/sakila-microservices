package com.example.sakila.module.staff;

import com.example.sakila.exception.DataConflictException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.staff.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

  private final StaffRepository staffRepository;

  @Autowired
  public StaffService(StaffRepository staffRepository) {
    this.staffRepository = staffRepository;
  }

  public Staff getStaffById(Long id) {
    if (id == null) return null;
    return staffRepository.getStaffById(id);
  }

  public List<Staff> getStaffByStoreId(Long storeId) {
    if (storeId == null) return null;
    return staffRepository.getStaffByStoreId(storeId);
  }

  public Staff createStaff(Staff staff) {
    return staffRepository.insertStaff(staff);
  }

  public Staff updateStaff(Long id, Staff source) {
    Staff target = getStaffById(id);
    if (target == null) throw new NotFoundException("Staff for ID " + id + " does not exist");

    target.setFirstName(source.getFirstName());
    target.setLastName(source.getLastName());
    target.setEmail(source.getEmail());
    target.setStoreId(source.getStoreId());
    target.setUserName(source.getUserName());
    target.setPassword(source.getPassword());
    target.setAddressId(source.getAddressId());

    if (source.getActive() != null) target.setActive(source.getActive());

    return staffRepository.updateStaff(target);
  }

  public void deleteStaff(Long id) {
    Staff staff = getStaffById(id);
    if (staff == null) throw new NotFoundException("Staff for ID " + id + " does not exist");

    try {
      staffRepository.deleteStaff(staff);
    } catch(DataIntegrityViolationException e) {
      throw new DataConflictException(e.getMessage(), e);
    }
  }
}
