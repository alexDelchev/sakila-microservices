package com.example.sakila.module.staff;

import com.example.sakila.event.bus.EventBus;
import com.example.sakila.generated.server.api.StaffApi;
import com.example.sakila.generated.server.model.*;
import com.example.sakila.module.staff.command.*;
import com.example.sakila.module.staff.command.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class StaffController  implements StaffApi {

  private static final ResponseEntity<Void> OK_RESPONSE = ResponseEntity.ok(null);

  private final EventBus eventBus;

  @Autowired
  public StaffController(@Qualifier("StaffEventBus") EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public ResponseEntity<Void> createStaff(CreateStaffCommandDTO dto) {
    CreateStaffCommand command = CommandUtils.toCreateStaffCommand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> deleteStaff(DeleteStaffCommandDTO dto) {
    DeleteStaffCommand command = CommandUtils.toDeleteStaffCommand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeActive(BasicBooleanCommandDTO dto) {
    ChangeActiveCommand command = CommandUtils.toChangeActiveCOmmand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeAddress(BasicInt64CommandDTO dto) {
    ChangeAddressCommand command = CommandUtils.toChangeAddressCommand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeEmail(BasicStringCommandDTO dto) {
    ChangeEmailCommand command = CommandUtils.toChangeEmailCommand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeFirstName(BasicStringCommandDTO dto) {
    ChangeFirstNameCommand command = CommandUtils.toChangeFirstNameCommand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeLastName(BasicStringCommandDTO dto) {
    ChangeLastNameCommand command = CommandUtils.toChangeLastNameCommand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changePassword(BasicStringCommandDTO dto) {
    ChangePasswordCommand command = CommandUtils.toChangePasswordCommand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeStore(BasicInt64CommandDTO dto) {
    ChangeStoreCommand command = CommandUtils.toChangeStoreCOmmand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeUsername(BasicStringCommandDTO dto) {
    ChangeUsernameCommand command = CommandUtils.toChangeUsernameCommand(dto);

    eventBus.emitSynchroniously(command);

    return OK_RESPONSE;
  }

}
