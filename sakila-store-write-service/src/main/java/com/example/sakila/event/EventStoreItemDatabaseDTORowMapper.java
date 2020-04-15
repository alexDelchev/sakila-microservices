package com.example.sakila.event;

import org.springframework.jdbc.core.RowMapper;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class EventStoreItemDatabaseDTORowMapper implements RowMapper<EventStoreItemDatabaseDTO> {

  @Override
  public EventStoreItemDatabaseDTO mapRow(ResultSet rs, int rowNum) {
    try {
      EventStoreItemDatabaseDTO result = new EventStoreItemDatabaseDTO();

      result.setEventId(UUID.fromString(rs.getString("eventId")));
      result.setAggregateId(rs.getLong("aggregateId"));
      result.setAggregateVersion(rs.getLong("aggregateVersion"));

      byte[] metaDataBytes = rs.getBytes("metaData");
      String metaDataJson = new String(metaDataBytes, StandardCharsets.UTF_8);
      result.setMetaDataJson(metaDataJson);

      byte[] eventBytes = rs.getBytes("data");
      String eventJson = new String(eventBytes, StandardCharsets.UTF_8);
      result.setEventJson(eventJson);

      result.setLastUpdate(rs.getDate("rowCreation"));

      return result;
    } catch (SQLException e) {
      throw new RuntimeException(String.format("Failed reading from database: %s", e.getMessage()), e);
    }
  }
}
