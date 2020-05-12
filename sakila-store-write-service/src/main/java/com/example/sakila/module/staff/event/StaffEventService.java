package com.example.sakila.module.staff.event;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.staff.StaffDTO;
import com.example.sakila.module.staff.StaffService;
import com.example.sakila.module.staff.StaffUtils;
import com.example.sakila.module.staff.StaffWriteModel;
import com.example.sakila.module.staff.event.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Service
public class StaffEventService {

  private static final String WRITE_TOPIC = "staff-store-write-staff-dto-stream";

  private static final String DELETE_TOPIC = "staff-store-write-staff-delete-stream";

  private final Logger log = LoggerFactory.getLogger(StaffEventService.class);

  private final EventBus eventBus;

  private final StaffService staffService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper mapper;

  @Autowired
  public StaffEventService(
      @Qualifier("StaffEventBus") EventBus eventBus,
      StaffService staffService,
      KafkaTemplate<String, String> kafkaTemplate,
      ObjectMapper objectMapper
  ) {
    this.eventBus = eventBus;
    this.staffService = staffService;
    this.kafkaTemplate = kafkaTemplate;
    this.mapper = objectMapper;
  }

  @PostConstruct
  private void postConstruct() {
    eventBus.register(this);
  }

  @Handler
  public void onStaffCreatedEvent(StaffCreatedEvent event) {
    publishDTO(WRITE_TOPIC, event.getId(), event.getStaffId(), event.getVersion());
  }

  @Handler
  public void onStaffDeletedEvent(StaffDeletedEvent event) {
    String json = toJson(event);
    publish(DELETE_TOPIC, json);
  }

  @Handler
  public void onActiveChangedEvent(ActiveChangedEvent event) {
    publishDTO(WRITE_TOPIC, event.getId(), event.getStaffId(), event.getVersion());
  }

  @Handler
  public void onAddressChangedEvent(AddressChangedEvent event) {
    publishDTO(WRITE_TOPIC, event.getId(), event.getStaffId(), event.getVersion());
  }

  @Handler
  public void onEmailChangedEvent(EmailChangedEvent event) {
    publishDTO(WRITE_TOPIC, event.getId(), event.getStaffId(), event.getVersion());
  }

  @Handler
  public void onFirstNameChangedEvent(FirstNameChangedEvent event) {
    publishDTO(WRITE_TOPIC, event.getId(), event.getStaffId(), event.getVersion());
  }

  @Handler
  public void onLastNameChangedEvent(LastNameChangedEvent event) {
    publishDTO(WRITE_TOPIC, event.getId(), event.getStaffId(), event.getVersion());
  }

  @Handler
  public void onPasswordChangedEvent(PasswordChangedEvent event) {
    publishDTO(WRITE_TOPIC, event.getId(), event.getStaffId(), event.getVersion());
  }

  @Handler
  public void onStoreChangedEvent(StoreChangedEvent event) {
    publishDTO(WRITE_TOPIC, event.getId(), event.getStaffId(), event.getVersion());
  }

  @Handler
  public void onUsernameChangedEvent(UsernameChangedEvent event) {
    publishDTO(WRITE_TOPIC, event.getId(), event.getStaffId(), event.getVersion());
  }

  private void publish(String topic, String message) {
    log.info("Publishing to ({}): {}", topic, message);
    kafkaTemplate.send(topic, message);
  }

  private void publishDTO(String topic, UUID eventId, Long staffId, Long version) {
    String json = generateEventMessageJson(eventId, staffId, version);
    publish(topic, json);
  }
  
  private String generateEventMessageJson(UUID eventId, Long staffId, Long staffVersion) {
    StaffEventMessage message = generateEventMessage(eventId, staffId, staffVersion);
    
    return toJson(message);
  }
  
  private StaffEventMessage generateEventMessage(UUID eventId, Long staffId, Long staffVersion) {
    StaffDTO dto = getStaffDTO(staffId, eventId);

    StaffEventMessage eventMessage = new StaffEventMessage();
    
    eventMessage.setEventId(eventId);
    eventMessage.setStaffDTO(dto);
    eventMessage.setStaffVersion(staffVersion);
    
    return eventMessage;
  }

  private StaffDTO getStaffDTO(Long staffId, UUID eventId) {
    StaffWriteModel model = staffService.getStaffAtEvent(staffId, eventId);
    return StaffUtils.toDTO(model);
  }

  private String toJson(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialise to JSON", e);
    }
  }
}
