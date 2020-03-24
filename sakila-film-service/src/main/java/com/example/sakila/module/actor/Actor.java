package com.example.sakila.module.actor;

import java.util.Date;

public class Actor {

  private Long id;

  private String firstName;

  private String lastName;

  private Date lastUpdate;

  Actor() {
  }

  Actor(Long id) {
    this.id = id;
  }

  Actor(Long id, String firstName, String lastName, Date lastUpdate) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.lastUpdate = lastUpdate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdated) {
    this.lastUpdate = lastUpdated;
  }
}
