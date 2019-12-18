package com.example.sakila.module.staff;

import com.example.sakila.module.staff.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
