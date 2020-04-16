package com.example.sakila.module.store.repository;

import com.example.sakila.module.store.Store;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Repository
public class StoreRepositoryHibernateImpl implements StoreRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Store getStoreById(Long id) {
    return entityManager.find(Store.class, id);
  }


  @Override
  public Store getStoreByAddressId(Long addressId) {
    TypedQuery<Store> query = createQuery("SELECT s FROM Store s WHERE s.address_id = :addressId");
    query.setParameter("addressId", addressId);
    return query.getSingleResult();
  }

  @Override
  public Store getStoreByManagerStaffId(Long managerStaffId) {
    TypedQuery<Store> query = createQuery("SELECT s FROM Store s WHERE s.managerStaff.id = :managerId");
    query.setParameter("managerId", managerStaffId);
    return query.getSingleResult();
  }

  @Override
  @Transactional
  public Store insertStore(Store store) {
    entityManager.persist(store);
    entityManager.flush();
    entityManager.refresh(store);
    return store;
  }

  @Override
  @Transactional
  public Store updateStore(Store store) {
    entityManager.merge(store);
    entityManager.flush();
    entityManager.refresh(store);
    return store;
  }

  @Override
  @Transactional
  public void deleteStore(Store store) {
    entityManager.remove(store);
  }

  private TypedQuery<Store> createQuery(String query) {
    return entityManager.createQuery(query, Store.class);
  }
}
