package com.example.sakila.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler({NotFoundException.class})
  public ErrorResponse handleNotFoundException(Exception exception) {
    return generateResponse(exception);
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({BadRequestException.class})
  public ErrorResponse handleBadRequestException(Exception exception) {
    return generateResponse(exception);
  }

  private ErrorResponse generateResponse(Exception exception) {
    return new ErrorResponse(exception.getMessage());
  }
}
