package com.example.sakila.module.staff;

import com.example.sakila.event.EventService;
import com.example.sakila.module.staff.StaffWriteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {

  private final EventService eventService;

  @Autowired
  public StaffService(EventService eventService) {
    this.eventService = eventService;
  }

  public StaffWriteModel getStaffById(Long id) {
    if (id == null) return null;
    StaffWriteModel staff = new StaffWriteModel();
    staff.setId(id);
    eventService.getEventsForAggregate(id, StaffWriteModel.class).forEach(e -> e.apply(staff));

    return staff;
  }
}
