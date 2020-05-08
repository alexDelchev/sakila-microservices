package com.example.sakila.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalControllerExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException::class)
  fun handleNotFoundException(exception: Exception): ErrorResponse = generateResponse(exception)

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException::class)
  fun handleBadRequestException(exception: Exception): ErrorResponse = generateResponse(exception)

  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(DataConflictException::class)
  fun handleConflictException(exception: Exception): ErrorResponse = generateResponse(exception)

  private fun generateResponse(exception: Exception): ErrorResponse = ErrorResponse(exception.message?:"Error:")

}
