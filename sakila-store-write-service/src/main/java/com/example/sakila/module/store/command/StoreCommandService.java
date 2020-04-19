package com.example.sakila.module.store.command;

import com.example.sakila.event.EventService;
import com.example.sakila.event.bus.EventBus;
import com.example.sakila.event.bus.Handler;
import com.example.sakila.exception.NotFoundException;
import com.example.sakila.module.staff.StaffWriteModel;
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

  @Handler
  public Long onCreateStoreCommand(CreateStoreCommand command) {
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
    checkAggregateExistence(command.getStoreId(), StoreWriteModel.class, "Store");

    processBasicCommand(command, new AddressChangedEvent());
  }

  @Handler
  public void onChangeManagerCommand(ChangeManagerCommand command) {
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
