package com.example.sakila.module.country

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = 'country')
class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = 'country_id')
  Long id

  @Column(name = 'country')
  String country

  @Column(name = 'last_update')
  @Temporal(TemporalType.TIMESTAMP)
  Date lastUpdate

  Country() {}

  Country(Long id) {
    this.id = id
  }

  Country(Long id, String country, Date lastUpdate) {
    this.id = id
    this.country = country
    this.lastUpdate = lastUpdate
  }
  
}
