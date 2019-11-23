package com.example.sakila.module.address.repository;

import com.example.sakila.module.address.Address;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AddressRepositoryHibernateImpl implements AddressRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Address getAddressById(Long id) {
    return entityManager.find(Address.class, id);
  }

  @Override
  public List<Address> getAddressesByCity(Long cityId) {
    TypedQuery<Address> query = createQuery("SELECT a FROM Address a WHERE city_id = :cityId");
    query.setParameter("cityId", cityId);
    return query.getResultList();
  }

  @Override
  public List<Address> getAddressesByCountry(Long countryId) {
    TypedQuery<Address> query = createQuery(
        "SELECT a FROM Address a JOIN a.city c JOIN c.country co WHERE co.id = :countryId"
    );
    query.setParameter("countryId", countryId);
    return query.getResultList();
  }

  private TypedQuery<Address> createQuery(String query) {
    return entityManager.createQuery(query, Address.class);
  }


}
