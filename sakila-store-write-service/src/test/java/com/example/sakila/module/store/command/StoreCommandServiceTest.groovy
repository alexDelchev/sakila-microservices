package com.example.sakila.module.store.command

import com.example.sakila.event.EventService
import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.staff.StaffWriteModel
import com.example.sakila.module.store.command.model.ChangeAddressCommand
import com.example.sakila.module.store.StoreWriteModel
import com.example.sakila.module.store.command.model.ChangeManagerCommand
import com.example.sakila.module.store.command.model.DeleteStoreCommand
import spock.lang.Specification

class StoreCommandServiceTest extends Specification {

  private EventService eventService = Mock(EventService)

  private EventBus eventBus = Mock(EventBus)

  private StoreCommandService storeCommandService = new StoreCommandService(eventBus, eventService)

  def "OnDeleteStoreCommand"() {
    given:
    Long nonExistingStoreId = -1L
    eventService.aggregateExists(nonExistingStoreId, StoreWriteModel.class) >> false
    DeleteStoreCommand deleteStoreCommand = new DeleteStoreCommand(
        storeId: nonExistingStoreId
    )

    when:
    storeCommandService.onDeleteStoreCommand(deleteStoreCommand)

    then:
    thrown NotFoundException
  }

  def "OnChangeAddressCommand"() {
    given:
    Long nonExistingStoreId = -1L
    eventService.aggregateExists(nonExistingStoreId, StoreWriteModel.class) >> false
    ChangeAddressCommand changeAddressCommand = new ChangeAddressCommand(
        storeId: nonExistingStoreId,
        newValue: 1L
    )

    when:
    storeCommandService.onChangeAddressCommand(changeAddressCommand)

    then:
    thrown NotFoundException
  }

  def "OnChangeManagerCommand - should throw NotFoundException on storeId"() {
    given:
    Long nonExistingStoreId = -1L
    eventService.aggregateExists(nonExistingStoreId, StoreWriteModel.class) >> false
    ChangeManagerCommand changeManagerCommand = new ChangeManagerCommand(
        storeId: nonExistingStoreId,
        newValue: 1L
    )

    when:
    storeCommandService.onChangeManagerCommand(changeManagerCommand)

    then:
    thrown NotFoundException
  }

  def "OnChangeManagerCommand - should throw NotFoundException on staffId"() {
    given:
    Long existingStoreId = 1L
    Long nonExistingStaffId = -1L;
    eventService.aggregateExists(existingStoreId, StoreWriteModel.class) >> true
    eventService.aggregateExists(nonExistingStaffId, StaffWriteModel.class) >> false
    ChangeManagerCommand changeManagerCommand = new ChangeManagerCommand(
        storeId: existingStoreId,
        newValue: nonExistingStaffId
    )

    when:
    storeCommandService.onChangeManagerCommand(changeManagerCommand)

    then:
    thrown NotFoundException
  }
}
