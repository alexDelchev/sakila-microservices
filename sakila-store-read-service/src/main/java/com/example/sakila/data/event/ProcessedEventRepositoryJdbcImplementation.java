package com.example.sakila.data.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class ProcessedEventRepositoryJdbcImplementation implements ProcessedEventRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public ProcessedEventRepositoryJdbcImplementation(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void insertProcessedEvent(UUID eventId, Long aggregateId, Long aggregateVersion) {
    String statement = "INSERT INTO processed_event(event_id, aggregate_id, aggregate_version) VALUES (?, ?, ?)";

    jdbcTemplate.update(statement, eventId, aggregateId, aggregateVersion);
  }

  public Boolean processedEventExists(UUID eventId) {
    String query = "SELECT EXISTS(SELECT 1 FROM processed_event WHERE event_id = ? LIMIT 1) AS exists";

    return jdbcTemplate.queryForObject(query, Boolean.class, eventId);
  }

  public Long getAggregateVersion(Long aggregateId) {
    String query = "SELECT MAX(aggregate_version) FROM processed_event WHERE aggregate_id = ?";

    return jdbcTemplate.queryForObject(query, Long.class, aggregateId);
  }
}
