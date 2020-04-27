package com.example.sakila.config;

import org.springframework.util.ErrorHandler;

public class TaskSchedulerErrorHandler implements ErrorHandler {

  @Override
  public void handleError(Throwable throwable) {
    throwable.printStackTrace();
  }
}
