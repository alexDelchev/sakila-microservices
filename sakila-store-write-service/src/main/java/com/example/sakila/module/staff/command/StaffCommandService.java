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

  @Handler
  public void onChangeActiveCommand(ChangeActiveCommand command) {
    processBasicCommand(command, new ActiveChangedEvent());
  }

  @Handler
  public void onChangeAddressCommand(ChangeAddressCommand command) {
    processBasicCommand(command, new AddressChangedEvent());
  }

  @Handler
  public void onChangeEmailCommand(ChangeEmailCommand command) {
    processBasicCommand(command, new EmailChangedEvent());
  }

  @Handler
  public void onChangeFirstNameCommand(ChangeFirstNameCommand command) {
    processBasicCommand(command, new FirstNameChangedEvent());
  }

  @Handler
  public void onChangeLastNameCommand(ChangeLastNameCommand command) {
    processBasicCommand(command, new LastNameChangedEvent());
  }

  @Handler
  public void onChangePasswordCommand(ChangePasswordCommand command) {
    processBasicCommand(command, new PasswordChangedEvent());
  }

  @Handler
  public void onChangeStoreCommand(ChangeStoreCommand command) {
    processBasicCommand(command, new StoreChangedEvent());
  }

  @Handler
  public void onChangeUsernameCommand(ChangeUsernameCommand command) {
    processBasicCommand(command, new UsernameChangedEvent());
  }

  private <T> void processBasicCommand(BasicStaffCommand<T> command, BasicStaffEvent<T> event) {
    event.setStaffId(command.getStaffId());
    event.setNewValue(command.getNewValue());

    Long aggregateVersion = eventService.getAggregateVersion(command.getStaffId());
    event.setVersion(aggregateVersion + 1L);

    eventService.persistEvent(event.getStaffId(), event);
    eventBus.emit(event);
  }
}
