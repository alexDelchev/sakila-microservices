package com.example.sakila.data.event

import spock.lang.Specification

class ProcessedEventServiceTest extends Specification {

  private final ProcessedEventRepository repository = Mock(ProcessedEventRepository)

  private final ProcessedEventService service = new ProcessedEventService(repository)

  def "should return version 0 for non existing aggregate"() {
    given:
    Long nonExistingAggregateId = -1L

    when:
    Long aggregateVersion = service.getAggregateVersion(nonExistingAggregateId)

    then:
    aggregateVersion == 0
  }
}
