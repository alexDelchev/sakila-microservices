package com.example.sakila.config;

import org.springframework.boot.task.TaskSchedulerCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.ErrorHandler;

@Configuration
public class TaskSchedulerConfig implements TaskSchedulerCustomizer {

  @Override
  public void customize(ThreadPoolTaskScheduler taskScheduler) {
    ErrorHandler errorHandler = new TaskSchedulerErrorHandler();
    taskScheduler.setErrorHandler(errorHandler);
  }
}
