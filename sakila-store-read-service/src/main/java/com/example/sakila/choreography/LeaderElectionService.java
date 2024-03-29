package com.example.sakila.choreography;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LeaderElectionService {

  private final static long DEFAULT_ELECTION_TIMEOUT = 1_500L;

  private final static String LEADER_LATCH_BASE_PATH = "/sakila-store-read/leader/";

  private final Logger log = LoggerFactory.getLogger(LeaderElectionService.class);

  private final CuratorFramework curatorFramework;

  @Autowired
  public LeaderElectionService(CuratorFramework curatorFramework) {
    this.curatorFramework = curatorFramework;
  }

  public void runAsLeader(String operationName, LeaderOperation operation) {
    runAsLeader(operationName, DEFAULT_ELECTION_TIMEOUT, operation);
  }

  public void runAsLeader(String operationName, Long timeoutMillis, LeaderOperation operation) {
    String fullPath = LEADER_LATCH_BASE_PATH + operationName;
    LeaderLatch leaderLatch = new LeaderLatch(curatorFramework, fullPath);
    try {
      long electionTimeStart = System.currentTimeMillis();
      leaderLatch.start();

      while (!leaderLatch.getLeader().isLeader()) {
        Thread.sleep(100);
        checkWaitTime(operationName, electionTimeStart, timeoutMillis);
      }

      if (leaderLatch.hasLeadership()) {
        log.info("Running {} as elected leader", operationName);
        operation.run();
      } else {
        long awaitTimeout = electionTimeStart + timeoutMillis - System.currentTimeMillis();
        leaderLatch.await(awaitTimeout, TimeUnit.MILLISECONDS);
      }

      leaderLatch.close(LeaderLatch.CloseMode.NOTIFY_LEADER);
    } catch (Exception e) {
      throw new RuntimeException(String.format("Failed running operation %s", operationName), e);
    }
  }

  private void checkWaitTime(
      String operationName,
      long waitStart,
      long timeoutMillis
  ) throws LeaderElectionTimeoutException {
    if (System.currentTimeMillis() - waitStart < timeoutMillis) return;

    String timeoutMessage = String.format(
        "Leader election for operation %s timed out after %d ms",
        operationName,
        timeoutMillis
    );

    throw new LeaderElectionTimeoutException(timeoutMessage);
  }
}
