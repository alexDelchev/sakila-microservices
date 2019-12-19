package com.example.sakila.module.store;

import com.example.sakila.module.staff.Staff;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "store")
public class Store {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "store_id")
  private Long id;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "manager_staff_id", referencedColumnName = "staff_id")
  private Staff managerStaff;

  @Column(name = "address_id")
  private Long address_id;

  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  Store() {}

  Store(Long id) {
    this.id = id;
  }

  Store(Long id, Staff managerStaff, Long address_id, Date lastUpdate) {
    this.id = id;
    this.managerStaff = managerStaff;
    this.address_id = address_id;
    this.lastUpdate = lastUpdate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Staff getManagerStaff() {
    return managerStaff;
  }

  public void setManagerStaff(Staff managerStaff) {
    this.managerStaff = managerStaff;
  }

  public Long getAddress_id() {
    return address_id;
  }

  public void setAddress_id(Long address_id) {
    this.address_id = address_id;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
