package com.example.sakila.bus;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EventBus {

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
      handlers.get(type).forEach(h -> CompletableFuture.runAsync(() -> h.invoke(object)));
    }
  }

  public void emitSynchronously(Object object) {
    Class<?> type = object.getClass();

    if (handlers.containsKey(type)) {
      handlers.get(type).forEach(h -> h.invoke(object));
    }
  }

  public void register(Object object) {
    List<HandlerMethod> eventHandlers = getEventHandlerMethods(object);

    eventHandlers.forEach(h -> {
      if (handlers.containsKey(h.getParameterType())) {
        handlers.get(h.getParameterType()).add(h);
      } else {
        handlers.put(h.getParameterType(), Arrays.asList(h));
      }
    });
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
