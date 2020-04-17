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
  public Long onCreateStaffCommand(CreateStaffCommand command) {
    Long staffId = eventService.persistAggregate(StaffWriteModel.class.getTypeName());
    Long version = 1L;

    StaffCreatedEvent event = new StaffCreatedEvent();
    event.setStaffId(staffId);
    event.setVersion(version);
    event.setFirstName(command.getFirstName());
    event.setLastName(command.getLastName());
    event.setAddress_id(command.getAddressId());
    event.setEmail(command.getEmail());
    event.setStoreId(command.getStoreId());
    event.setActive(command.getActive());
    event.setUserName(command.getUserName());
    event.setPassword(command.getPassword());

    eventService.persistEvent(event.getStaffId(), event);
    eventBus.emit(event);

    return staffId;
  }

  @Handler
  public void onDeleteStaffCommand(DeleteStaffCommand command) {
    Long staffId = command.getStaffId();
    eventService.deleteEventsForAggregate(staffId);
    eventService.deleteAggregate(staffId);

    StaffDeletedEvent event = new StaffDeletedEvent();
    event.setStaffId(staffId);
    eventBus.emit(event);
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
