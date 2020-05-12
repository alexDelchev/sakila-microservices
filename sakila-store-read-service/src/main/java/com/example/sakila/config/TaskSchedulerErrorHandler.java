package com.example.sakila.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class TaskSchedulerErrorHandler implements ErrorHandler {

  private final Logger log = LoggerFactory.getLogger(TaskSchedulerErrorHandler.class);

  @Override
  public void handleError(Throwable throwable) {
    log.error("Exception whil executing scheduled task", throwable);
  }
}
