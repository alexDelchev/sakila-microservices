package com.example.sakila.event

import com.example.sakila.event.store.EventStore
import spock.lang.Specification

class EventServiceTest extends Specification {

  private EventStore eventStore = Mock(EventStore)

  private EventService eventService = new EventService(eventStore)

  def "GetAggregateVersion"() {
    given:
    Long nonExistingAggregateId = -1L
    eventStore.getAggregateVersion(nonExistingAggregateId) >> null

    when:
    Long version = eventService.getAggregateVersion(nonExistingAggregateId)

    then:
    version == 0L
  }
}
