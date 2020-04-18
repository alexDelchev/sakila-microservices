package com.example.sakila.data.staff;

import com.example.sakila.module.staff.Staff;
import com.example.sakila.module.staff.StaffService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StaffStateKeeper {

  private static final String GROUP_ID = "sakila_store_read_service";

  private static final String CREATE_TOPIC = "staff-create-event-stream";

  private static final String UPDATE_TOPIC = "staff-dto-stream";

  private static final String DELETE_TOPIC = "staff-delete-event-stream";

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final StaffService staffService;

  @Autowired
  public StaffStateKeeper(StaffService staffService) {
    this.staffService = staffService;
  }

  private Staff convertJsonToModel(String json) {
    StaffDTO dto = deserialize(json, StaffDTO.class);
    return fromDTO(dto);
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
