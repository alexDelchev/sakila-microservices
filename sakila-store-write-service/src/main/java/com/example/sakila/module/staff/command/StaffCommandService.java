package com.example.sakila.module.staff.command;

import com.example.sakila.event.EventService;
import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.staff.StaffWriteModel;
import com.example.sakila.module.staff.command.model.*;
import com.example.sakila.module.staff.event.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StaffCommandService {

  private final EventBus eventBus;

  private final EventService eventService;

  @Autowired
  public StaffCommandService(
      @Qualifier("StaffEventBus") EventBus eventBus,
      EventService eventService
  ) {
    this.eventBus = eventBus;
    this.eventService = eventService;

    this.eventBus.register(this);
  }
  
}
