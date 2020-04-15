package com.example.sakila.module.staff.command;

import com.example.sakila.generated.server.model.*;
import com.example.sakila.module.staff.command.model.*;

public class CommandUtils {

  public static CreateStaffCommand toCreateStaffCommand(CreateStaffCommandDTO dto) {
    CreateStaffCommand command = new CreateStaffCommand();

    command.setFirstName(dto.getFirstName());
    command.setLastName(dto.getLastName());
    command.setAddressId(dto.getAddressId());
    command.setEmail(dto.getEmail());
    command.setStoreId(dto.getStoreId());
    command.setActive(dto.isActive());
    command.setUserName(dto.getUserName());
    command.setPassword(dto.getPassword());

    return command;
  }

  public static DeleteStaffCommand toDeleteStaffCommand(DeleteStaffCommandDTO dto) {
    DeleteStaffCommand command = new DeleteStaffCommand();

    command.setStaffId(dto.getStaffId());

    return command;
  }

  public static ChangeActiveCommand toChangeActiveCOmmand(BasicBooleanCommandDTO dto) {
    ChangeActiveCommand command = new ChangeActiveCommand();

    copyProperties(dto, command);

    return command;
  }

  public static ChangeAddressCommand toChangeAddressCommand(BasicInt64CommandDTO dto) {
    ChangeAddressCommand command = new ChangeAddressCommand();

    copyProperties(dto, command);

    return command;
  }

  public static ChangeEmailCommand toChangeEmailCommand(BasicStringCommandDTO dto) {
    ChangeEmailCommand command = new ChangeEmailCommand();

    copyProperties(dto, command);

    return command;
  }

  public static ChangeFirstNameCommand toChangeFirstNameCommand(BasicStringCommandDTO dto) {
    ChangeFirstNameCommand command = new ChangeFirstNameCommand();

    copyProperties(dto, command);

    return command;
  }

  public static ChangeLastNameCommand toChangeLastNameCommand(BasicStringCommandDTO dto) {
    ChangeLastNameCommand command = new ChangeLastNameCommand();

    copyProperties(dto, command);

    return command;
  }

  public static ChangePasswordCommand toChangePasswordCommand(BasicStringCommandDTO dto) {
    ChangePasswordCommand command = new ChangePasswordCommand();

    copyProperties(dto, command);

    return command;
  }

  public static ChangeStoreCommand toChangeStoreCOmmand(BasicInt64CommandDTO dto) {
    ChangeStoreCommand command = new ChangeStoreCommand();

    copyProperties(dto, command);

    return command;
  }

  public static ChangeUsernameCommand toChangeUsernameCommand(BasicStringCommandDTO dto) {
    ChangeUsernameCommand command = new ChangeUsernameCommand();

    copyProperties(dto, command);

    return command;
  }

  private static <T extends BasicStaffCommand<Boolean>> void copyProperties(BasicBooleanCommandDTO dto, T command) {
    command.setStaffId(dto.getAggregateId());
    command.setNewValue(dto.isNewValue());
  }

  private static <T extends BasicStaffCommand<Long>> void copyProperties(BasicInt64CommandDTO dto, T command) {
    command.setStaffId(dto.getAggregateId());
    command.setNewValue(dto.getNewValue());
  }

  private static <T extends BasicStaffCommand<String>> void copyProperties(BasicStringCommandDTO dto, T command) {
    command.setStaffId(dto.getAggregateId());
    command.setNewValue(dto.getNewValue());
  }
}
