package com.example.sakila.exception

class ErrorResponse {

  String message

  Date date

  ErrorResponse(String message) {
    this.message = message
    this.date = new Date()
  }

  ErrorResponse(String message, Date date) {
    this.message = message
    this.date = date
  }
}
