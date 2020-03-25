package com.example.sakila.module.actor;

import org.bson.types.ObjectId;

import java.util.Date;

public class Actor {

  private ObjectId id;

  private String firstName;

  private String lastName;

  private Date lastUpdate;

  public Actor() {
  }

  public Actor(ObjectId id) {
    this.id = id;
  }

  public Actor(ObjectId id, String firstName, String lastName, Date lastUpdate) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.lastUpdate = lastUpdate;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
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
