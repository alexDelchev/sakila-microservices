package com.example.sakila.event

import com.example.sakila.event.store.EventStore
import spock.lang.Specification

class EventServiceTest extends Specification {

  private EventStore eventStore = Mock(EventStore)

  private EventService eventService = new EventService(eventStore)
  
}
