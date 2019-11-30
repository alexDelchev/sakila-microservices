package com.example.sakila.module.country.repository;

import com.example.sakila.module.country.Country;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CountryRepositoryHibernateImpl implements CountryRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Country getCountryById(Long id) {
    return entityManager.find(Country.class, id);
  }

  @Override
  public Country getCountryByAddressId(Long addressId) {
    TypedQuery<Country> query = createQuery("SELECT c FROM Address a JOIN a.city ci JOIN ci.country c WHERE a.id = :addressId");
    query.setParameter("addressId", addressId);
    return query.getSingleResult();
  }

  @Override
  public List<Country> getAllCountries() {
    return createQuery("SELECT c FROM Country c").getResultList();
  }

  private TypedQuery<Country> createQuery(String query) {
    return entityManager.createQuery(query, Country.class);
  }
}