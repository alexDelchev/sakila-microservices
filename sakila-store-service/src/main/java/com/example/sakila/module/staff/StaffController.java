package com.example.sakila.module.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StaffController {

  private final StaffService staffService;

  @Autowired
  public StaffController(StaffService staffService) {
    this.staffService = staffService;
  }
  
}
