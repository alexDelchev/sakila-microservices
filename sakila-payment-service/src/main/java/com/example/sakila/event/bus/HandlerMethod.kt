package com.example.sakila.event.bus

import java.lang.reflect.Method

class HandlerMethod(
    val owner: Any,
    val method: Method,
    val parameterType: Class<*>
) {

  fun invoke(parameter: Any) {
    method.invoke(owner, parameter)
  }
}
