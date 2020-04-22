package com.example.sakila.module.store.repository;

import com.example.sakila.module.store.Store;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StoreRepositoryJdbcImplementation implements StoreRepository {

  private final RowMapper<Store> rowMapper = new StoreRowMapper();

  private final JdbcTemplate jdbcTemplate;

  public StoreRepositoryJdbcImplementation(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Store getStoreById(Long id) {
    String query = "SELECT store_id, manager_staff_id, address_id, last_update FROM store WHERE store_id = ?";

    try {
      return jdbcTemplate.queryForObject(query, rowMapper, id);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public Store getStoreByAddressId(Long addressId) {
    String query = "SELECT store_id, manager_staff_id, address_id, last_update FROM store WHERE address_id = ?";

    return jdbcTemplate.queryForObject(query, rowMapper, addressId);
  }

  @Override
  public Store getStoreByManagerStaffId(Long managerStaffId) {
    String query = "SELECT store_id, manager_staff_id, address_id, last_update FROM store WHERE manager_staff_id = ?";

    try {
      return jdbcTemplate.queryForObject(query, rowMapper, managerStaffId);
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  @Override
  public Store insertStore(Store store) {
    String statement = "INSERT INTO store(store_id, manager_staff_id, address_id) VALUES (?, ?, ?)";

    jdbcTemplate.update(statement, store.getId(), store.getManagerStaffId(), store.getAddressId());

    return getStoreById(store.getId());
  }

  @Override
  public Store updateStore(Store store) {
    String statement = "UPDATE store SET manager_staff_id = ?, address_id = ? WHERE store_id = ?";

    jdbcTemplate.update(statement, store.getManagerStaffId(), store.getAddressId(), store.getId());

    return getStoreById(store.getId());
  }

  @Override
  public void deleteStore(Store store) {
    String statment = "DELETE FROM store WHERE store_id = ?";

    jdbcTemplate.update(statment, store.getId());
  }
}
