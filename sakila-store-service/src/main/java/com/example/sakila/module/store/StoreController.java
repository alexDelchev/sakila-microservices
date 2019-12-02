package com.example.sakila.module.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class StoreController {

  private final StoreService storeService;

  @Autowired
  public StoreController(StoreService storeService) {
    this.storeService = storeService;
  }

}
