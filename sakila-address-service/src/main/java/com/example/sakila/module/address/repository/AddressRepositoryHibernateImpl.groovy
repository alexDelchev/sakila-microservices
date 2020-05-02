package com.example.sakila.module.address.repository

import com.example.sakila.module.address.Address
import org.springframework.stereotype.Repository

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class AddressRepositoryHibernateImpl implements AddressRepository {

  @PersistenceContext
  private EntityManager entityManager

  @Override
  Address getAddressById(Long id) {
    entityManager.find(Address, id)
  }

  @Override
  List<Address> getAddressesByCity(Long cityId) {
    TypedQuery<Address> query = createQuery('''
      SELECT 
        a 
      FROM 
        Address a 
      WHERE 
        city_id = :cityId'''
    )
    query.setParameter('cityId', cityId)

    query.resultList
  }

  @Override
  List<Address> getAddressesByCountry(Long countryId) {
    TypedQuery<Address> query = createQuery('''
      SELECT 
        a 
      FROM 
        Address a 
      JOIN 
        a.city c 
      JOIN 
        c.country co 
      WHERE 
        co.id = :countryId'''
    )
    query.setParameter('countryId', countryId)

    query.resultList
  }

  @Override
  @Transactional
  Address insertAddress(Address address) {
    entityManager.persist(address)
    entityManager.flush()
    entityManager.refresh(address)
    address
  }

  @Override
  @Transactional
  Address updateAddress(Address address) {
    entityManager.merge(address)
    entityManager.flush()
    entityManager.refresh(address)
    address
  }

  @Override
  @Transactional
  void deleteAddress(Address address) {
    entityManager.remove(address)
  }

  private TypedQuery<Address> createQuery(String query) {
    entityManager.createQuery(query, Address)
  }

}
