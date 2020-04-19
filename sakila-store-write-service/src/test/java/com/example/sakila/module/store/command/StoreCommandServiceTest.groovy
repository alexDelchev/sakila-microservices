package com.example.sakila.module.store.command

import com.example.sakila.event.EventService
import com.example.sakila.event.bus.EventBus
import spock.lang.Specification

class StoreCommandServiceTest extends Specification {

  private EventService eventService = Mock(EventService)

  private EventBus eventBus = Mock(EventBus)

  private StoreCommandService storeCommandService = new StoreCommandService(eventBus, eventService)
  
}
