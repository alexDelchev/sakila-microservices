package com.example.sakila.event.bus

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class HandlerMethod {

  final Object owner

  final Method method

  final Class<?> parameterType

  HandlerMethod(Object owner, Method method, Class<?> parameterType) {
    this.owner = owner
    this.method = method
    this.parameterType = parameterType
  }

  void invoke(Object parameter) {
    try {
      this.method.invoke(owner, parameter)
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException('Failed to invoke event handler method', e)
    }
  }

}
