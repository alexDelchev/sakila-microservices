package com.example.sakila.exception

class DataConflictException extends RuntimeException {

  DataConflictException(String message) {
    super(message)
  }

  DataConflictException(String message, Throwable throwable) {
    super(message, throwable)
  }
}
