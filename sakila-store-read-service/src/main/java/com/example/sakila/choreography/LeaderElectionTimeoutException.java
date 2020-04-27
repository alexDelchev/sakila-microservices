package com.example.sakila.choreography;

public class LeaderElectionTimeoutException extends Exception {

  public LeaderElectionTimeoutException() {
    super();
  }

  public LeaderElectionTimeoutException(String message) {
    super(message);
  }

  public LeaderElectionTimeoutException(Throwable throwable) {
    super(throwable);
  }

  public LeaderElectionTimeoutException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
