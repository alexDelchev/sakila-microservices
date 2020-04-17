package com.example.sakila.module.staff;

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

  private final StaffCommandService commandService;

  @Autowired
  public StaffController(@Qualifier("StaffEventBus") StaffCommandService commandService) {
    this.commandService = commandService;
  }

  @Override
  public ResponseEntity<AggregateIdDTO> createStaff(CreateStaffCommandDTO dto) {
    CreateStaffCommand command = CommandUtils.toCreateStaffCommand(dto);

    Long createdStaffId = commandService.onCreateStaffCommand(command);

    AggregateIdDTO response = new AggregateIdDTO().aggregateId(createdStaffId);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> deleteStaff(DeleteStaffCommandDTO dto) {
    DeleteStaffCommand command = CommandUtils.toDeleteStaffCommand(dto);

    commandService.onDeleteStaffCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeActive(BasicBooleanCommandDTO dto) {
    ChangeActiveCommand command = CommandUtils.toChangeActiveCOmmand(dto);

    commandService.onChangeActiveCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeAddress(BasicInt64CommandDTO dto) {
    ChangeAddressCommand command = CommandUtils.toChangeAddressCommand(dto);

    commandService.onChangeAddressCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeEmail(BasicStringCommandDTO dto) {
    ChangeEmailCommand command = CommandUtils.toChangeEmailCommand(dto);

    commandService.onChangeEmailCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeFirstName(BasicStringCommandDTO dto) {
    ChangeFirstNameCommand command = CommandUtils.toChangeFirstNameCommand(dto);

    commandService.onChangeFirstNameCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeLastName(BasicStringCommandDTO dto) {
    ChangeLastNameCommand command = CommandUtils.toChangeLastNameCommand(dto);

    commandService.onChangeLastNameCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changePassword(BasicStringCommandDTO dto) {
    ChangePasswordCommand command = CommandUtils.toChangePasswordCommand(dto);

    commandService.onChangePasswordCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeStore(BasicInt64CommandDTO dto) {
    ChangeStoreCommand command = CommandUtils.toChangeStoreCOmmand(dto);

    commandService.onChangeStoreCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeUsername(BasicStringCommandDTO dto) {
    ChangeUsernameCommand command = CommandUtils.toChangeUsernameCommand(dto);

    commandService.onChangeUsernameCommand(command);

    return OK_RESPONSE;
  }

}
