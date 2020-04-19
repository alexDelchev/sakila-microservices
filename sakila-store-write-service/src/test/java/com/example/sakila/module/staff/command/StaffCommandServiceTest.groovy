package com.example.sakila.module.staff.command

import com.example.sakila.event.EventService
import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.staff.StaffWriteModel
import com.example.sakila.module.staff.command.model.ChangeActiveCommand
import com.example.sakila.module.staff.command.model.ChangeAddressCommand
import com.example.sakila.module.staff.command.model.ChangeEmailCommand
import com.example.sakila.module.staff.command.model.ChangeFirstNameCommand
import com.example.sakila.module.staff.command.model.DeleteStaffCommand
import spock.lang.Specification

class StaffCommandServiceTest extends Specification {

  private EventBus eventBus = Mock(EventBus)

  private EventService eventService = Mock(EventService)

  private StaffCommandService commandService = new StaffCommandService(eventBus, eventService)

  def "OnDeleteStaffCommand"() {
    given:
    Long nonExistingStaffId = -1L
    eventService.aggregateExists(nonExistingStaffId, StaffWriteModel.class) >> false
    DeleteStaffCommand command = new DeleteStaffCommand(
        staffId: nonExistingStaffId
    )

    when:
    commandService.onDeleteStaffCommand(command)

    then:
    thrown NotFoundException
  }

  def "OnChangeActiveCommand"() {
    given:
    Long nonExistingStaffId = -1L
    eventService.aggregateExists(nonExistingStaffId, StaffWriteModel.class) >> false
    ChangeActiveCommand command = new ChangeActiveCommand(
        staffId: nonExistingStaffId
    )

    when:
    commandService.onChangeActiveCommand(command)

    then:
    thrown NotFoundException
  }

  def "OnChangeAddressCommand"() {
    given:
    Long nonExistingStaffId = -1L
    eventService.aggregateExists(nonExistingStaffId, StaffWriteModel.class) >> false
    ChangeAddressCommand command = new ChangeAddressCommand(
        staffId: nonExistingStaffId
    )

    when:
    commandService.onChangeAddressCommand(command)

    then:
    thrown NotFoundException
  }

  def "OnChangeEmailCommand"() {
    given:
    Long nonExistingStaffId = -1L
    eventService.aggregateExists(nonExistingStaffId, StaffWriteModel.class) >> false
    ChangeEmailCommand command = new ChangeEmailCommand(
        staffId: nonExistingStaffId
    )

    when:
    commandService.onChangeEmailCommand(command)

    then:
    thrown NotFoundException
  }

  def "OnChangeFirstNameCommand"() {
    given:
    Long nonExistingStaffId = -1L
    eventService.aggregateExists(nonExistingStaffId, StaffWriteModel.class) >> false
    ChangeFirstNameCommand command = new ChangeFirstNameCommand(
        staffId: nonExistingStaffId
    )

    when:
    commandService.onChangeFirstNameCommand(command)

    then:
    thrown NotFoundException
  }
}
