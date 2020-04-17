package com.example.sakila.module.store.repository;

import com.example.sakila.module.store.Store;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StoreRowMapper implements RowMapper<Store> {

  @Override
  public Store mapRow(ResultSet resultSet, int i) throws SQLException {
    Store store = new Store();

    store.setId(resultSet.getLong("store_id"));
    store.setManagerStaffId(resultSet.getLong("manager_staff_id"));
    store.setAddressId(resultSet.getLong("address_id"));
    store.setLastUpdate(resultSet.getDate("last_update"));

    return store;
  }
}
