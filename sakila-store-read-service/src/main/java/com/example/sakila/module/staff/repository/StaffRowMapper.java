package com.example.sakila.module.staff.repository;

import com.example.sakila.module.staff.Staff;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffRowMapper implements RowMapper<Staff> {

  @Override
  public Staff mapRow(ResultSet resultSet, int i) throws SQLException {
    Staff staff = new Staff();

    staff.setId(resultSet.getLong("staff_id"));
    staff.setFirstName(resultSet.getString("first_name"));
    staff.setLastName(resultSet.getString("last_name"));
    staff.setAddressId(resultSet.getLong("address_id"));
    staff.setEmail(resultSet.getString("email"));
    staff.setStoreId(resultSet.getLong("store_id"));
    staff.setActive(resultSet.getBoolean("active"));
    staff.setUserName(resultSet.getString("username"));
    staff.setPassword(resultSet.getString("password"));
    staff.setLastUpdate(resultSet.getDate("last_update"));

    return staff;
  }
}
