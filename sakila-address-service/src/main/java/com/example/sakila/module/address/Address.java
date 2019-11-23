package com.example.sakila.module.address;

import com.example.sakila.module.city.City;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "address_id")
  private Long id;

  @Column(name = "address")
  private String address;

  @Column(name = "address2")
  private String address2;

  @Column(name = "district")
  private String district;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "city_id")
  private City city;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "phone")
  private String phone;

  @Column(name = "last_update")
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastUpdate;

  Address() {}

  Address(Long id) {
    this.id = id;
  }

  Address(Long id, String address, String address2, City city, String postalCode, String phone, Date lastUpdate) {
    this.id = id;
    this.address = address;
    this.address2 = address2;
    this.city = city;
    this.postalCode = postalCode;
    this.phone = phone;
    this.lastUpdate = lastUpdate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Date getLastUpdate() {
    return lastUpdate;
  }

  public void setLastUpdate(Date lastUpdate) {
    this.lastUpdate = lastUpdate;
  }
}
