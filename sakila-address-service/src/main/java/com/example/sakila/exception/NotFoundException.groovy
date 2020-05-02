package com.example.sakila.exception

class NotFoundException extends RuntimeException {

  NotFoundException(String message) {
    super(message)
  }

  NotFoundException(String message, Throwable throwable) {
    super(message, throwable)
  }
}
