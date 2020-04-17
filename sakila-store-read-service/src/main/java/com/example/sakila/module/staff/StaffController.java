package com.example.sakila.module.staff;

import com.example.sakila.exception.NotFoundException;
import com.example.sakila.generated.server.api.StaffApi;
import com.example.sakila.generated.server.model.StaffDTO;
import com.example.sakila.module.store.Store;
import com.example.sakila.module.store.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StaffController implements StaffApi {

  private final StaffService staffService;

  private final StoreService storeService;

  @Autowired
  public StaffController(StaffService staffService, StoreService storeService) {
    this.staffService = staffService;
    this.storeService = storeService;
  }

  @Override
  public ResponseEntity<StaffDTO> getStaffById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(staffService.getStaffById(id)));
  }

  @Override
  public ResponseEntity<List<StaffDTO>> getStaffByStoreId(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
        staffService.getStaffByStoreId(id).stream().map(this::toDTO).collect(Collectors.toList())
    );
  }

  @Override
  public ResponseEntity<StaffDTO> createStaff(@RequestBody StaffDTO staffDTO) {
    return ResponseEntity.ok(toDTO(staffService.createStaff(toEntity(staffDTO))));
  }

  @Override
  public ResponseEntity<StaffDTO> replaceStaff(@PathVariable("id") Long id,@RequestBody StaffDTO staffDTO) {
    return ResponseEntity.ok(toDTO(staffService.updateStaff(id, toEntity(staffDTO))));
  }

  @Override
  public ResponseEntity<Void> deleteStaff(@PathVariable("id") Long id) {
    staffService.deleteStaff(id);
    return ResponseEntity.ok(null);
  }

  private StaffDTO toDTO(Staff staff) {
    StaffDTO staffDTO = new StaffDTO();

    staffDTO.setId(staff.getId());
    staffDTO.setFirstName(staff.getFirstName());
    staffDTO.setLastName(staff.getLastName());
    staffDTO.setAddressId(staff.getAddressId());
    staffDTO.setEmail(staff.getEmail());
    staffDTO.setStoreId(staff.getStoreId());
    staffDTO.setActive(staff.getActive());
    staffDTO.setUserName(staff.getUserName());
    staffDTO.setPassword(staff.getPassword());
    staffDTO.setLastUpdate(OffsetDateTime.ofInstant(staff.getLastUpdate().toInstant(), ZoneId.systemDefault()));

    return staffDTO;
  }

  private Staff toEntity(StaffDTO staffDTO) {
    Staff staff = new Staff();

    staff.setId(staffDTO.getId());
    staff.setFirstName(staffDTO.getFirstName());
    staff.setLastName(staffDTO.getLastName());
    staff.setAddressId(staffDTO.getAddressId());
    staff.setEmail(staffDTO.getEmail());
    staff.setActive(staffDTO.isActive());
    staff.setUserName(staffDTO.getUserName());
    staff.setPassword(staff.getPassword());
    staff.setAddressId(staffDTO.getAddressId());
    if (staffDTO.getLastUpdate() != null) staff.setLastUpdate(Date.from(staffDTO.getLastUpdate().toInstant()));

    return staff;
  }
}
