package com.example.sakila.module.store.command;

import com.example.sakila.event.EventService;
import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.staff.StaffWriteModel;
import com.example.sakila.module.store.StoreWriteModel;
import com.example.sakila.module.store.command.model.*;
import com.example.sakila.module.store.event.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StoreCommandService {

  private final Logger log = LoggerFactory.getLogger(StoreCommandService.class);

  private final EventBus eventBus;

  private final EventService eventService;

  @Autowired
  public StoreCommandService(
      @Qualifier("StoreEventBus") EventBus eventBus,
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
  public Long onCreateStoreCommand(CreateStoreCommand command) {
    log.info("Received CreateStoreCommand");
    Long storeId = eventService.persistAggregate(StoreWriteModel.class.getTypeName());
    Long version = 1L;

    StoreCreatedEvent event = new StoreCreatedEvent();
    event.setStoreId(storeId);
    event.setVersion(version);
    event.setManagerStaffId(command.getManagerStaffID());
    event.setAddressId(command.getAddressId());

    eventService.persistEvent(event.getStoreId(), event);
    eventBus.emit(event);

    return storeId;
  }

  @Handler
  public void onDeleteStoreCommand(DeleteStoreCommand command) {
    log.info("Received DeleteStoreCommand");
    checkAggregateExistence(command.getStoreId(), StoreWriteModel.class, "Store");

    Long storeId = command.getStoreId();
    eventService.deleteAggregate(storeId);
    eventService.deleteEventsForAggregate(storeId);

    StoreDeletedEvent storeDeletedEvent = new StoreDeletedEvent();
    storeDeletedEvent.setStoreId(command.getStoreId());
    eventBus.emit(storeDeletedEvent);
  }

  @Handler
  public void onChangeAddressCommand(ChangeAddressCommand command) {
    log.info("Received ChangeAddressCommand");
    checkAggregateExistence(command.getStoreId(), StoreWriteModel.class, "Store");

    processBasicCommand(command, new AddressChangedEvent());
  }

  @Handler
  public void onChangeManagerCommand(ChangeManagerCommand command) {
    log.info("Received ChangeManagerCommand");
    checkAggregateExistence(command.getStoreId(), StoreWriteModel.class, "Store");
    if (command.getNewValue() != null) {
      checkAggregateExistence(command.getNewValue(), StaffWriteModel.class, "Staff");
    }

    processBasicCommand(command, new ManagerChangedEvent());
  }

  private <T> void checkAggregateExistence(Long aggregateId, Class<T> type, String domainName) {
    if (!eventService.aggregateExists(aggregateId, type)) {
      throw new NotFoundException(String.format("%s for ID %d does not exist", domainName, aggregateId));
    }
  }

  private <T> void processBasicCommand(BasicStoreCommand<T> command, BasicStoreEvent<T> event) {
    event.setStoreId(command.getStoreId());
    event.setNewValue(command.getNewValue());

    Long aggregateVersion = eventService.getAggregateVersion(command.getStoreId());
    event.setVersion(aggregateVersion + 1L);

    eventService.persistEvent(event.getStoreId(), event);
    eventBus.emit(event);
  }
}
