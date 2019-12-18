package com.example.sakila.module.inventory;

import com.example.sakila.module.store.Store;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "inventory")
public class Inventory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "inventory_id")
  private Long id;

  @Column(name = "film_id")
  private Long film_id;

  @OneToOne
  @JoinColumn(name = "store_id")
  private Store store;

  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  public Inventory() {}

  public Inventory(Long id) {
    this.id = id;
  }

  public Inventory(Long id, Long film_id, Store store, Date lastUpdate) {
    this.id = id;
    this.film_id = film_id;
    this.store = store;
    this.lastUpdate = lastUpdate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getFilm_id() {
    return film_id;
  }

  public void setFilm_id(Long film_id) {
    this.film_id = film_id;
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
