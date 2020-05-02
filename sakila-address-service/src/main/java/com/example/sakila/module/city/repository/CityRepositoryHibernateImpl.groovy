package com.example.sakila.module.city.repository

import com.example.sakila.module.city.City
import org.springframework.stereotype.Repository

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class CityRepositoryHibernateImpl implements CityRepository {

  @PersistenceContext
  private EntityManager entityManager

  @Override
  City getCityById(Long id) {
    entityManager.find(City, id)
  }

  @Override
  City getCityByAddressId(Long addressId) {
    TypedQuery<City> query = createQuery('SELECT c FROM Address a JOIN a.city c WHERE a.address_id = :addressId')
    query.setParameter('addressId', addressId)

    query.singleResult
  }

  @Override
  List<City> getCitiesByCountry(Long countryId) {
    TypedQuery<City> query = createQuery('SELECT c FROM City c WHERE c.country.id = :countryId')
    query.setParameter('countryId', countryId)

    query.resultList
  }

  @Override
  List<City> getAllCities() {
    createQuery('SELECT c FROM City c').resultList
  }

  @Override
  @Transactional
  City insertCity(City city) {
    entityManager.persist(city)
    entityManager.flush()
    entityManager.refresh(city)
    city
  }

  @Override
  @Transactional
  City updateCity(City city) {
    entityManager.merge(city)
    entityManager.flush()
    entityManager.refresh(city)
    city
  }

  @Override
  @Transactional
  void deleteCity(City city) {
    entityManager.remove(city)
  }

  private TypedQuery<City> createQuery(String query) {
    entityManager.createQuery(query, City.class)
  }
}
