package com.example.sakila.module.city

import com.example.sakila.module.country.Country
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate

import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = 'city')
class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = 'city_id')
  Long id

  @Column(name = 'city')
  String city

  @ManyToOne(cascade = CascadeType.REFRESH)
  @JoinColumn(name = 'country_id')
  Country country

  @Column(name = 'last_update')
  @Temporal(TemporalType.TIMESTAMP)
  Date lastUpdate

  City() {}

  City(Long id) {
    this.id = id
  }

  City(Long id, String city, Country country, Date lastUpdate) {
    this.id = id
    this.city = city
    this.country = country
    this.lastUpdate = lastUpdate
  }

}
