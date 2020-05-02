package com.example.sakila.exception

class BadRequestException extends RuntimeException {

  BadRequestException(String message) {
    super(message)
  }

  BadRequestException(String message, Throwable throwable) {
    super(message, throwable)
  }
}
