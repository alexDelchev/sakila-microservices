package com.example.sakila.module.store.command;

import com.example.sakila.event.EventService;
import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.module.store.StoreWriteModel;
import com.example.sakila.module.store.command.model.*;
import com.example.sakila.module.store.event.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class StoreCommandService {

  private final EventBus eventBus;

  private final EventService eventService;

  @Autowired
  public StoreCommandService(
      @Qualifier("StoreEventBus") EventBus eventBus,
      EventService eventService
  ) {
    this.eventBus = eventBus;
    this.eventService = eventService;

    this.eventBus.register(this);
  }
  
}
