package com.example.sakila.module.country.repository

import com.example.sakila.module.country.Country
import org.springframework.stereotype.Repository

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class CountryRepositoryHibernateImpl implements CountryRepository {

  @PersistenceContext
  private EntityManager entityManager

  @Override
  Country getCountryById(Long id) {
    entityManager.find(Country, id)
  }

  @Override
  Country getCountryByAddressId(Long addressId) {
    TypedQuery<Country> query = createQuery('SELECT c FROM Address a JOIN a.city ci JOIN ci.country c WHERE a.id = :addressId')
    query.setParameter('addressId', addressId)
    
    query.singleResult
  }

  @Override
  List<Country> getAllCountries() {
    createQuery('SELECT c FROM Country c').getResultList()
  }

  @Override
  @Transactional
  Country insertCountry(Country country) {
    entityManager.persist(country)
    entityManager.flush()
    entityManager.refresh(country)
    country
  }

  @Override
  @Transactional
  Country updateCountry(Country country) {
    entityManager.merge(country)
    entityManager.flush()
    entityManager.refresh(country)
    country
  }

  @Override
  @Transactional
  void deleteCountry(Country country) {
    entityManager.remove(country)
  }

  private TypedQuery<Country> createQuery(String query) {
    entityManager.createQuery(query, Country)
  }
}
