package com.example.sakila.module.store.command;

import com.example.sakila.generated.server.model.BasicInt64CommandDTO;
import com.example.sakila.generated.server.model.CreateStoreCommandDTO;
import com.example.sakila.generated.server.model.DeleteStoreCommandDTO;
import com.example.sakila.module.store.command.model.*;

public class CommandUtils {

  public static CreateStoreCommand toCreateStoreCommand(CreateStoreCommandDTO dto) {
    CreateStoreCommand command = new CreateStoreCommand();

    command.setAddressId(dto.getAddressId());
    command.setManagerStaffID(dto.getManagerStaffId());

    return command;
  }

  public static DeleteStoreCommand toDeleteStoreCommand(DeleteStoreCommandDTO dto) {
    DeleteStoreCommand command = new DeleteStoreCommand();

    command.setStoreId(dto.getStoreId());

    return command;
  }

  public static ChangeAddressCommand toChangeAddressCommand(BasicInt64CommandDTO dto) {
    ChangeAddressCommand command = new ChangeAddressCommand();

    copyProperties(dto, command);

    return command;
  }

  public static ChangeManagerCommand toChangeManagerCommand(BasicInt64CommandDTO dto) {
    ChangeManagerCommand command = new ChangeManagerCommand();

    copyProperties(dto, command);

    return command;
  }

  private static <T extends BasicStoreCommand<Long>> void copyProperties(BasicInt64CommandDTO dto, T command) {
    command.setStoreId(dto.getAggregateId());
    command.setNewValue(dto.getNewValue());
  }
}
