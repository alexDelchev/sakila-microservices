package com.example.sakila.module.language;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "language")
public class Language {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "language_id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  public Language() {}

  public Language(Long id) {
    this.id = id;
  }

  public Language(Long id, String name, Date lastUpdate) {
    this.id = id;
    this.name = name;
    this.lastUpdate = lastUpdate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
