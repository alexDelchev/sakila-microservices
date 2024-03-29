package com.example.sakila.module.staff.command;

import com.example.sakila.event.EventService;
import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.exception.BadRequestException;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.staff.StaffWriteModel;
import com.example.sakila.module.staff.command.model.*;
import com.example.sakila.module.staff.event.model.*;
import com.example.sakila.module.store.StoreWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StaffCommandService {

  private final Logger log = LoggerFactory.getLogger(StaffCommandService.class);

  private final EventBus eventBus;

  private final EventService eventService;

  @Autowired
  public StaffCommandService(
      @Qualifier("StaffEventBus") EventBus eventBus,
      EventService eventService
  ) {
    this.eventBus = eventBus;
    this.eventService = eventService;
  }

  @PostConstruct
  private void postConstruct() {
    eventBus.register(this);
  }

  @Handler
  public Long onCreateStaffCommand(CreateStaffCommand command) {
    log.info("Received CreateStaffCommand");
    Long staffId = eventService.persistAggregate(StaffWriteModel.class.getTypeName());
    Long version = 1L;

    StaffCreatedEvent event = new StaffCreatedEvent();
    event.setStaffId(staffId);
    event.setVersion(version);
    event.setFirstName(command.getFirstName());
    event.setLastName(command.getLastName());
    event.setAddressId(command.getAddressId());
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
    log.info("Received DeleteStaffCommand");
    checkAggregateExistence(command.getStaffId(), StaffWriteModel.class, "Staff");

    Long staffId = command.getStaffId();
    eventService.deleteEventsForAggregate(staffId);
    eventService.deleteAggregate(staffId);

    StaffDeletedEvent event = new StaffDeletedEvent();
    event.setStaffId(staffId);
    eventBus.emit(event);
  }

  @Handler
  public void onChangeActiveCommand(ChangeActiveCommand command) {
    log.info("Received ChangeActiveCommand");
    checkAggregateExistence(command.getStaffId(), StaffWriteModel.class, "Staff");

    processBasicCommand(command, new ActiveChangedEvent());
  }

  @Handler
  public void onChangeAddressCommand(ChangeAddressCommand command) {
    log.info("Received ChangeAddressCommand");
    checkAggregateExistence(command.getStaffId(), StaffWriteModel.class, "Staff");

    processBasicCommand(command, new AddressChangedEvent());
  }

  @Handler
  public void onChangeEmailCommand(ChangeEmailCommand command) {
    log.info("Received ChangeEmailCommand");
    checkAggregateExistence(command.getStaffId(), StaffWriteModel.class, "Staff");

    processBasicCommand(command, new EmailChangedEvent());
  }

  @Handler
  public void onChangeFirstNameCommand(ChangeFirstNameCommand command) {
    log.info("Received ChangeFirstNameCommand");
    checkAggregateExistence(command.getStaffId(), StaffWriteModel.class, "Staff");

    processBasicCommand(command, new FirstNameChangedEvent());
  }

  @Handler
  public void onChangeLastNameCommand(ChangeLastNameCommand command) {
    log.info("Received ChangeLastNameCommand");
    checkAggregateExistence(command.getStaffId(), StaffWriteModel.class, "Staff");

    processBasicCommand(command, new LastNameChangedEvent());
  }

  @Handler
  public void onChangePasswordCommand(ChangePasswordCommand command) {
    log.info("Received ChangePasswordCommand");
    checkAggregateExistence(command.getStaffId(), StaffWriteModel.class, "Staff");

    processBasicCommand(command, new PasswordChangedEvent());
  }

  @Handler
  public void onChangeStoreCommand(ChangeStoreCommand command) {
    log.info("Received ChangeStoreCommand");
    checkAggregateExistence(command.getStaffId(), StaffWriteModel.class, "Staff");

    if (command.getNewValue() != null) {
      checkAggregateExistence(command.getNewValue(), StoreWriteModel.class, "Store");
    }

    processBasicCommand(command, new StoreChangedEvent());
  }

  @Handler
  public void onChangeUsernameCommand(ChangeUsernameCommand command) {
    log.info("Received ChangeUsernameCommand");
    checkAggregateExistence(command.getStaffId(), StaffWriteModel.class, "Staff");

    processBasicCommand(command, new UsernameChangedEvent());
  }

  private <T> void checkAggregateExistence(Long aggregateId, Class<T> type, String domainName) {
    if (!eventService.aggregateExists(aggregateId, type)) {
      throw new NotFoundException(String.format("%s for ID %d does not exist", domainName, aggregateId));
    }
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
