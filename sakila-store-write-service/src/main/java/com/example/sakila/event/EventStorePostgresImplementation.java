package com.example.sakila.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class EventStorePostgresImplementation implements EventStore {

  private final JdbcTemplate jdbcTemplate;

  private final RowMapper<EventStoreItemDatabaseDTO> rowMapper = new EventStoreItemDatabaseDTORowMapper();

  @Autowired
  public EventStorePostgresImplementation(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  
}
