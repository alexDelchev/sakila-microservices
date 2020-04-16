package com.example.sakila.module.store;

import com.example.sakila.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {

  private final EventService eventService;

  @Autowired
  public StoreService(EventService eventService) {
    this.eventService = eventService;
  }

  public StoreWriteModel getStoreById(Long id) {
    if (id == null) return null;
    StoreWriteModel model = new StoreWriteModel();
    model.setId(id);
    eventService.getEventsForAggregate(id, StoreWriteModel.class).forEach(e -> e.apply(model));

    return model;
  }
}
