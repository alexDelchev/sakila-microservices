package com.example.sakila.module.staff.repository;

import com.example.sakila.module.staff.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StaffRepositoryJdbcImplementation implements StaffRepository {

  private final RowMapper<Staff> rowMapper = new StaffRowMapper();

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public StaffRepositoryJdbcImplementation(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Staff getStaffById(Long id) {
    String query = "SELECT " +
            "staff_id, first_name, last_name, address_id, email, store_id, active, username, password, last_update " +
            "FROM staff " +
            "WHERE staff_id = ?" ;

    try {
      return jdbcTemplate.queryForObject(query, rowMapper, id);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public List<Staff> getStaffByStoreId(Long id) {
    String query = "SELECT " +
        "staff_id, first_name, last_name, address_id, email, store_id, active, username, password, last_update " +
        "FROM staff " +
        "WHERE store_id = ?" ;

    return jdbcTemplate.query(query, rowMapper, id);
  }

  @Override
  public Staff insertStaff(Staff staff) {
    String statement = "INSERT INTO staff(" +
        "staff_id, first_name, last_name, address_id, email, store_id, active, username, password" +
        ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    jdbcTemplate.update(
        statement,
        staff.getId(), staff.getFirstName(), staff.getLastName(),
        staff.getAddressId(), staff.getEmail(), staff.getStoreId(),
        staff.getActive(), staff.getUserName(), staff.getPassword()
    );

    return getStaffById(staff.getId());
  }

  @Override
  public Staff updateStaff(Staff staff) {
    String statement = "UPDATE staff SET " +
        "first_name = ?, " +
        "last_name = ?, " +
        "address_id = ?, " +
        "email = ?, " +
        "store_id = ?, " +
        "active = ?, " +
        "username = ? ," +
        "password = ? " +
        "WHERE staff_id = ?";

    jdbcTemplate.update(
        statement,
        staff.getFirstName(), staff.getLastName(), staff.getAddressId(),
        staff.getEmail(), staff.getStoreId(), staff.getActive(),
        staff.getUserName(), staff.getPassword(), staff.getId()
    );

    return getStaffById(staff.getId());
  }

  @Override
  public void deleteStaff(Staff staff) {
    String statement = "DELETE FROM staff WHERE staff_id = ?";

    jdbcTemplate.update(statement, staff.getId());
  }
}
