package com.example.sakila.module.staff;

import com.example.sakila.generated.server.api.StaffApi;
import com.example.sakila.generated.server.model.StaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Controller
public class StaffController implements StaffApi {

  private final StaffService staffService;

  @Autowired
  public StaffController(StaffService staffService) {
    this.staffService = staffService;
  }

  @Override
  public ResponseEntity<StaffDTO> getStaffById(@PathVariable("id") Long id) {
    return ResponseEntity.ok(toDTO(staffService.getStaffById(id)));
  }

  private StaffDTO toDTO(Staff staff) {
    StaffDTO staffDTO = new StaffDTO();
    staffDTO.setId(staff.getId());
    staffDTO.setFirstName(staff.getFirstName());
    staffDTO.setLastName(staff.getLastName());
    staffDTO.setAddressId(staff.getAddress_id());
    staffDTO.setEmail(staff.getEmail());
    staffDTO.setStoreId(staff.getStore().getId());
    staffDTO.setActive(staff.getActive());
    staffDTO.setUserName(staff.getUserName());
    staffDTO.setPassword(staff.getPassword());
    staffDTO.setLastUpdate(OffsetDateTime.ofInstant(staff.getLastUpdate().toInstant(), ZoneId.systemDefault()));
    return staffDTO;
  }
}
