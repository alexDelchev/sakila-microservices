package com.example.sakila.module.staff.command

import com.example.sakila.event.EventService
import com.example.sakila.event.bus.EventBus
import com.example.sakila.exception.NotFoundException
import com.example.sakila.module.staff.StaffWriteModel
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
}
