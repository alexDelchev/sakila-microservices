package com.example.sakila.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalControllerExceptionHandler {

  private val log = LoggerFactory.getLogger(GlobalControllerExceptionHandler::class.java)

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

  private fun generateResponse(exception: Exception): ErrorResponse {
    log.error("Error while processing request: ", exception)
    return ErrorResponse(exception.message?:"Error:")
  }

}
