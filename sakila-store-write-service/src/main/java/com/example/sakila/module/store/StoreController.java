package com.example.sakila.module.store;

import com.example.sakila.generated.server.api.StoreApi;
import com.example.sakila.generated.server.model.AggregateIdDTO;
import com.example.sakila.generated.server.model.BasicInt64CommandDTO;
import com.example.sakila.generated.server.model.CreateStoreCommandDTO;
import com.example.sakila.generated.server.model.DeleteStoreCommandDTO;
import com.example.sakila.module.store.command.*;
import com.example.sakila.module.store.command.model.ChangeAddressCommand;
import com.example.sakila.module.store.command.model.ChangeManagerCommand;
import com.example.sakila.module.store.command.model.CreateStoreCommand;
import com.example.sakila.module.store.command.model.DeleteStoreCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class StoreController implements StoreApi {

  private static final ResponseEntity<Void> OK_RESPONSE = ResponseEntity.ok(null);

  private final StoreCommandService commandService;

  @Autowired
  public StoreController(StoreCommandService commandService) {
    this.commandService = commandService;
  }

  @Override
  public ResponseEntity<AggregateIdDTO> createStore(CreateStoreCommandDTO dto) {
    CreateStoreCommand command = CommandUtils.toCreateStoreCommand(dto);

    Long createdStoreId = commandService.onCreateStoreCommand(command);

    AggregateIdDTO response = new AggregateIdDTO().aggregateId(createdStoreId);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Void> deleteStore(DeleteStoreCommandDTO dto) {
    DeleteStoreCommand command = CommandUtils.toDeleteStoreCommand(dto);

    commandService.onDeleteStoreCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeAddress(BasicInt64CommandDTO dto) {
    ChangeAddressCommand command = CommandUtils.toChangeAddressCommand(dto);

    commandService.onChangeAddressCommand(command);

    return OK_RESPONSE;
  }

  @Override
  public ResponseEntity<Void> changeManager(BasicInt64CommandDTO dto) {
    ChangeManagerCommand command = CommandUtils.toChangeManagerCommand(dto);

    commandService.onChangeManagerCommand(command);

    return OK_RESPONSE;
  }
}
