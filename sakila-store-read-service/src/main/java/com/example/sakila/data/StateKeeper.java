package com.example.sakila.data;

import com.example.sakila.choreography.LeaderElectionService;
import com.example.sakila.choreography.LeaderElectionTimeoutException;
import com.example.sakila.data.event.ProcessedEventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@EnableScheduling
public class StateKeeper {

  private static final String TRIGGER_EVENT_EMISSION_TOPIC = "store-store-write-events-trigger";

  private final Logger log = LoggerFactory.getLogger(StateKeeper.class);

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final ProcessedEventService eventService;

  private final LeaderElectionService leaderElectionService;

  private final ObjectMapper objectMapper;

  @Value("${eureka.instance.instance-id}")
  private String instanceId;

  @Autowired
  public StateKeeper(
      KafkaTemplate kafkaTemplate,
      ProcessedEventService eventService,
      LeaderElectionService leaderElectionService,
      ObjectMapper objectMapper
  ) {
    this.kafkaTemplate = kafkaTemplate;
    this.eventService = eventService;
    this.leaderElectionService = leaderElectionService;
    this.objectMapper = objectMapper;
  }

  @Scheduled(cron = "0/15 * * ? * *")
  private void triggerEventEmission() {
    leaderElectionService.runAsLeader("trigger-events", () -> {
      EmitEventsMessage message = new EmitEventsMessage();
      UUID latestEventId = eventService.getLatestProcessedEventId();
      message.setEventId(latestEventId);
      String payload = serialize(message);

      log.info("Publishing latest event ID as {}", latestEventId.toString());
      kafkaTemplate.send(TRIGGER_EVENT_EMISSION_TOPIC, payload);
    });
  }

  private String serialize(Object object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException("Failed to serialize to JSON", e);
    }
  }
}
