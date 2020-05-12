package com.example.sakila.event.bus

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.lang.IllegalArgumentException
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap

class EventBus(private val name: String) {

  private val log = LoggerFactory.getLogger(EventBus::class.java)

  private val handlers: MutableMap<Class<*>, MutableList<HandlerMethod>>

  init {
    this.handlers = ConcurrentHashMap()
  }

  fun hasHandlerForType(type: Class<*>): Boolean = handlers.containsKey(type)

  fun emit(payload: Any) {
    val type = payload::class.java

    if (handlers.containsKey(type)) {
      handlers[type]!!.forEach {
        log.info("Invoking {}::{} asynchronously", it.owner::class.java.name, it.method.name)
        GlobalScope.launch { it.invoke(payload)
        }
      }
    }
  }

  fun emitSynchronously(payload: Any) {
    val type = payload::class.java

    if (handlers.containsKey(type)) {
      handlers[type]!!.forEach {
        log.info("Invoking {}::{} synchronously", it.owner::class.java.name, it.method.name)
        it.invoke(payload)
      }
    }
  }

  private fun checkEventHandlerMethodParameters(method: Method) {
    if (method.parameterCount > 1) {
      throw IllegalArgumentException("Method annotated with @Handler has more than 1 parameter")
    }
  }

  private fun getEventHandlerMethods(component: Any): List<HandlerMethod> {
    return component::class.java.declaredMethods
        .filter { it.isAnnotationPresent(Handler::class.java) }
        .map {
          checkEventHandlerMethodParameters(it)
          HandlerMethod(component, it, it.parameterTypes[0])
        }
  }

  fun register(component: Any) {
    val eventHandlers = getEventHandlerMethods(component)

    eventHandlers.forEach { h ->
      if (handlers.containsKey(h.parameterType)) {
        handlers[h.parameterType]!!.add(h)
      } else {
        handlers[h.parameterType] = mutableListOf(h)
      }
    }
  }
}
