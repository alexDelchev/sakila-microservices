package com.example.sakila.module.address

import com.example.sakila.module.city.City
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = 'address')
class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = 'address_id')
  Long id

  @Column(name = 'address')
  String address

  @Column(name = 'address2')
  String address2

  @Column(name = 'district')
  String district

  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = 'city_id')
  City city

  @Column(name = 'postal_code')
  String postalCode

  @Column(name = 'phone')
  String phone

  @Column(name = 'last_update')
  @Temporal(TemporalType.TIMESTAMP)
  Date lastUpdate

  Address() {}

  Address(Long id) {
    this.id = id
  }

  Address(Long id, String address, String address2, City city, String postalCode, String phone, Date lastUpdate) {
    this.id = id
    this.address = address
    this.address2 = address2
    this.city = city
    this.postalCode = postalCode
    this.phone = phone
    this.lastUpdate = lastUpdate
  }
}
