package com.example.sakila.event.bus

import groovy.util.logging.Slf4j

import java.lang.reflect.Method
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ConcurrentHashMap

@Slf4j
class EventBus {

  private final Map<Class<?>, List<HandlerMethod>> handlers

  final String name

  EventBus(String name) {
    this.name = name
    handlers = new ConcurrentHashMap<>()
  }

  boolean hasHandlerForType(Class type) {
    type in handlers.keySet()
  }

  void emit(Object object) {
    Class<?> type = object.class

    if (handlers.containsKey(type)) {
      handlers[type].each { h ->
        log.info("Invoking ${h.owner.class.name}::${h.method.name} asynchronously")
        CompletableFuture.runAsync { h.invoke(object) }
      }
    }
  }

  void emitSynchronously(Object object) {
    Class<?> type = object.class

    if (handlers.containsKey(type)) {
      handlers[type].each { h ->
        log.info("Invoking ${h.owner.class.name}::${h.method.name} synchronously")
        h.invoke(object)
      }
    }
  }

  void register(Object object) {
    List<HandlerMethod> eventHandlers = getEventHandlerMethods(object)

    eventHandlers.each { h ->
      if (handlers.containsKey(h.parameterType)) {
        handlers[h.parameterType] << h
      } else {
        handlers[h.parameterType] = [h]
      }
    }
  }

  private List<HandlerMethod> getEventHandlerMethods(Object object) {
    object.class.declaredMethods.findAll { it.isAnnotationPresent(Handler) }.collect { m ->
      checkEventHandlerMethodParameters(m)
      new HandlerMethod(object, m, m.parameterTypes[0])
    }
  }

  private void checkEventHandlerMethodParameters(Method method) {
    if (method.parameterCount > 1) {
      throw new IllegalArgumentException('Method annotated with @Handler has more than 1 parameter')
    }
  }
}
