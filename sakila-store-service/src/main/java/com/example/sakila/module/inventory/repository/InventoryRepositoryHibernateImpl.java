package com.example.sakila.module.inventory.repository;

import com.example.sakila.module.inventory.Inventory;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class InventoryRepositoryHibernateImpl implements InventoryRepository {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public Inventory getInventoryById(Long id) {
    return entityManager.find(Inventory.class, id);
  }

  @Override
  public List<Inventory> getInventoriesByStore(Long storeId) {
    TypedQuery<Inventory> query = createQuery("SELECT i FROM Inventory i WHERE store_id = :storeId");
    query.setParameter("storeId", storeId);
    return query.getResultList();
  }

  @Override
  public List<Inventory> getInventoriesByFilm(Long filmId) {
    TypedQuery<Inventory> query = createQuery("SELECT i FROM Inventory i WHERE film_id = :filmId");
    query.setParameter("filmId", filmId);
    return query.getResultList();
  }

  @Override
  @Transactional
  public Inventory insertInventory(Inventory inventory) {
    entityManager.persist(inventory);
    entityManager.flush();
    entityManager.refresh(inventory);
    return inventory;
  }

  private TypedQuery<Inventory> createQuery(String query) {
    return entityManager.createQuery(query, Inventory.class);
  }
}
