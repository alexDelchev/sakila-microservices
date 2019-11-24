package com.example.sakila.module.city.repository;

import com.example.sakila.module.city.City;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CityRepositoryHibernateImpl implements CityRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public City getCityById(Long id) {
    return entityManager.find(City.class, id);
  }

  @Override
  public City getCityByAddressId(Long addressId) {
    TypedQuery<City> query = createQuery("SELECT c FROM Address a JOIN a.city c WHERE a.address_id = :addressId");
    query.setParameter("addressId", addressId);
    return query.getSingleResult();
  }

  @Override
  public List<City> getCitiesByCountry(Long countryId) {
    TypedQuery<City> query = createQuery("SELECT c FROM City WHERE c.country_id = :countryId");
    query.setParameter("countryId", countryId);
    return query.getResultList();
  }

  @Override
  public List<City> getAllCities() {
    return createQuery("SELECT c FROM City c").getResultList();
  }

  private TypedQuery<City> createQuery(String query) {
    return entityManager.createQuery(query, City.class);
  }
}
