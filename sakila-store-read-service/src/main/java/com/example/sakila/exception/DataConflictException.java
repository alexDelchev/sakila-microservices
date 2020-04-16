package com.example.sakila.exception;

public class DataConflictException extends RuntimeException {

  public DataConflictException(String message) {
    super(message);
  }

  public DataConflictException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
