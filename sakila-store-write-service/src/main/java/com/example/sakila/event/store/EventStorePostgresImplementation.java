package com.example.sakila.event.store;

import com.example.sakila.event.Constants;
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

  @Override
  public Boolean aggregateExists(Long aggregateId, String type) {
    String query = "SELECT EXISTS(SELECT 1 FROM aggregate WHERE aggregateId = ? and type = ?)";

    return jdbcTemplate.queryForObject(query, Boolean.class, aggregateId, type);
  }

  @Override
  public Long persistAggregate(String type, Date date) {
    String statement = "INSERT INTO aggregate (type, lastUpdate) VALUES (?, ?) RETURNING aggregateId";
    return jdbcTemplate.queryForObject(statement, Long.class, type, date);
  }

  @Override
  public Long getAggregateVersion(Long aggregateId) {
    String query = "SELECT MAX(aggregateVersion) FROM event WHERE aggregateId = ?";
    Long result;

    try {
      result = jdbcTemplate.queryForObject(query, Long.class, aggregateId);
    } catch (DataAccessException e) {
      result = null;
    }

    return result;
  }

  @Override
  public void deleteAggregate(Long aggregateId) {
    String statement = "DELETE FROM aggregate WHERE aggregateId = ?";

    jdbcTemplate.update(statement, aggregateId);
  }

  @Override
  public void persistEventStoreItem(EventStoreItemDatabaseDTO item){
    String statement =
        "INSERT INTO event(eventId, aggregateId, aggregateVersion, data, metaData, rowCreation) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    jdbcTemplate.update(
        statement,
        item.getEventId(),
        item.getAggregateId(),
        item.getAggregateVersion(),
        item.getEventJson().getBytes(Constants.CHARSET),
        item.getMetaDataJson().getBytes(Constants.CHARSET),
        item.getLastUpdate()
    );
  }

  @Override
  public List<EventStoreItemDatabaseDTO> getEventStoreItemsForAggregate(Long aggregateId) {
    String query = "SELECT eventId, aggregateId, aggregateVersion, data, metaData, rowCreation FROM event WHERE aggregateId = ?";

    return jdbcTemplate.query(query, rowMapper, aggregateId);
  }

  @Override
  public void deleteEventStoreItemsForAggregate(Long aggregateId) {
    String statement = "DELETE FROM event WHERE aggregateId = ?";

    jdbcTemplate.update(statement, aggregateId);
  }
}
