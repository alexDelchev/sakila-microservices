package com.example.sakila.event.bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EventBus {

  private final Logger log = LoggerFactory.getLogger(EventBus.class);

  private final String name;

  private final Map<Class<?>, List<HandlerMethod>> handlers;

  public EventBus(String name) {
    this.name = name;
    handlers = new ConcurrentHashMap<>();
  }

  public boolean hasHandlerForType(Class type) {
    return handlers.keySet().contains(type);
  }

  public void emit(Object object) {
    Class<?> type = object.getClass();

    if (handlers.containsKey(type)) {
      handlers.get(type).forEach(h -> {
        log.info("Invoking {}::{} asynchronously", h.getOwner().getClass().getName(), h.getMethod().getName());
        CompletableFuture.runAsync(() -> h.invoke(object));
      });
    }
  }

  public void emitSynchronously(Object object) {
    Class<?> type = object.getClass();

    if (handlers.containsKey(type)) {
      handlers.get(type).forEach(h -> {
        log.info("Invoking {}::{} synchronously", h.getOwner().getClass().getName(), h.getMethod().getName());
        h.invoke(object);
      });
    }
  }

  public void register(Object object) {
    log.info("Registering {}", object.getClass().getName());
    List<HandlerMethod> eventHandlers = getEventHandlerMethods(object);

    eventHandlers.forEach(h -> {
      if (!handlers.containsKey(h.getParameterType())) {
        handlers.put(h.getParameterType(), new ArrayList<>());
      }

      handlers.get(h.getParameterType()).add(h);
      log.info("Registered method {}", h.getMethod().getName());
    });
    log.info("Registered {}", object.getClass().getName());
  }

  private List<HandlerMethod> getEventHandlerMethods(Object object) {
    return Arrays.stream(object.getClass().getDeclaredMethods())
        .filter(m -> m.isAnnotationPresent(Handler.class))
        .map(m -> {
          checkEventHandlerMethodParameters(m);
          return new HandlerMethod(object, m, m.getParameterTypes()[0]);
        })
        .collect(Collectors.toList());
  }

  private void checkEventHandlerMethodParameters(Method method) {
    if (method.getParameterCount() > 1) {
      throw new IllegalArgumentException("Method annotated with @Handler has more than 1 parameter");
    }
  }

  public String getName() {
    return name;
  }
}
