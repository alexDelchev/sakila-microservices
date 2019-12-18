package com.example.sakila.module.staff.repository;

import com.example.sakila.module.staff.Staff;

import java.util.List;

public interface StaffRepository {

  Staff getStaffById(Long id);

  List<Staff> getStaffByStoreId(Long id);

  Staff insertStaff(Staff staff);
}
