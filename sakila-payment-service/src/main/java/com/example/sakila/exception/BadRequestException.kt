package com.example.sakila.exception

import java.lang.RuntimeException

class BadRequestException : RuntimeException {

  constructor(message: String) : super(message)

  constructor(message: String, throwable: Throwable) : super(message, throwable)

}
