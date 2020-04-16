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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StaffEventService {

  private static final String DTO_TOPIC = "staff-dto-stream";

  private final EventBus eventBus;

  private final StaffService staffService;

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public StaffEventService(
      @Qualifier("StaffEventBus") EventBus eventBus,
      StaffService staffService,
      KafkaTemplate<String, String> kafkaTemplate
  ) {
    this.eventBus = eventBus;
    this.staffService = staffService;
    this.kafkaTemplate = kafkaTemplate;

    this.eventBus.register(this);
  }

}
