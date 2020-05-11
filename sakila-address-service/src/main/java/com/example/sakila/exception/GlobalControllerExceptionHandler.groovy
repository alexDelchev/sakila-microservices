package com.example.sakila.exception

import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@Slf4j
@ControllerAdvice
class GlobalControllerExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler([NotFoundException])
  ErrorResponse handleNotFoundException(Exception exception) {
    generateResponse(exception)
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler([BadRequestException])
  ErrorResponse handleBadRequestException(Exception exception) {
    generateResponse(exception)
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler([DataConflictException])
  ErrorResponse handleConflictException(Exception exception) {
    generateResponse(exception)
  }

  private ErrorResponse generateResponse(Exception exception) {
    log.error("Exception thrown while processing request:", exception)
    new ErrorResponse(exception.getMessage())
  }
}
