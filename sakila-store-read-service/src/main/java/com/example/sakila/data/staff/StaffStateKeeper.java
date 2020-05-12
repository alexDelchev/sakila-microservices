package com.example.sakila.data.staff;

import com.example.sakila.data.event.ProcessedEventService;
import com.example.sakila.module.staff.Staff;
import com.example.sakila.module.staff.StaffService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StaffStateKeeper {

  private static final String GROUP_ID = "sakila_store_read_service";

  private static final String WRITE_TOPIC = "staff-store-write-staff-dto-stream";

  private static final String DELETE_TOPIC = "staff-store-write-staff-delete-stream";

  private final Logger log = LoggerFactory.getLogger(StaffStateKeeper.class);

  private final ObjectMapper objectMapper;

  private final StaffService staffService;

  private final ProcessedEventService eventService;

  @Autowired
  public StaffStateKeeper(StaffService staffService, ProcessedEventService eventService, ObjectMapper objectMapper) {
    this.staffService = staffService;
    this.eventService = eventService;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = {WRITE_TOPIC}, groupId = GROUP_ID, concurrency = "1")
  public void consumeStaffWriteEventStream(String message) {
    log.info("Consuming message from {}: {}", WRITE_TOPIC, message);
    StaffEventMessage eventMessage = deserialize(message, StaffEventMessage.class);
    if (isEventInvalidForProcessing(eventMessage)) return;

    Staff staff = fromDTO(eventMessage.getStaffDTO());
    if (staffService.getStaffById(staff.getId()) != null) {
      staffService.updateStaff(staff.getId(), staff);
    } else {
      staffService.createStaff(staff);
    }

    staffService.updateStaff(staff.getId(), staff);

    eventService.markEventAsProcessed(eventMessage.getEventId(), staff.getId(), eventMessage.getStaffVersion());
  }

  @KafkaListener(topics = {DELETE_TOPIC}, groupId = GROUP_ID)
  public void consumeStaffDeletedEventStream(String message) {
    log.info("Consuming message from {}: {}", DELETE_TOPIC, message);
    StaffDeletedEvent deletedEvent = deserialize(message, StaffDeletedEvent.class);

    staffService.deleteStaff(deletedEvent.getStaffId());
  }

  private boolean isEventInvalidForProcessing(StaffEventMessage eventMessage) {
    if (eventService.isEventProcessed(eventMessage.getEventId())) return true;

    Long currentVersion = eventService.getAggregateVersion(eventMessage.getStaffDTO().getId());

    return eventMessage.getStaffVersion() != (currentVersion + 1);
  }

  private <T> T deserialize(String json, Class<T> type) {
    try {
      return objectMapper.readValue(json, type);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(String.format("Failed parsing json: %s", json), e);
    }
  }

  private Staff fromDTO(StaffDTO dto) {
    Staff staff = new Staff();

    staff.setId(dto.getId());
    staff.setFirstName(dto.getFirstName());
    staff.setLastName(dto.getLastName());
    staff.setAddressId(dto.getAddressId());
    staff.setEmail(dto.getEmail());
    staff.setStoreId(dto.getStoreId());
    staff.setActive(dto.getActive());
    staff.setUserName(dto.getUserName());
    staff.setPassword(dto.getPassword());

    return staff;
  }
}
