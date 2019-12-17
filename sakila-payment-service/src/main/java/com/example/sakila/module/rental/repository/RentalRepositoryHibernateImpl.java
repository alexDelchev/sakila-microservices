package com.example.sakila.module.rental.repository;

import com.example.sakila.module.rental.Rental;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RentalRepositoryHibernateImpl implements RentalRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Rental getRentalById(Long id) {
    return entityManager.find(Rental.class, id);
  }

  @Override
  public List<Rental> getRentalsByInventoryId(Long id) {
    TypedQuery<Rental> query = createQuery("SELECT r FROM Rental r WHERE r.inventory_id = :inventoryId");
    query.setParameter("inventoryId", id);
    return query.getResultList();
  }

  @Override
  public List<Rental> getRentalsByCustomerId(Long id) {
    TypedQuery<Rental> query = createQuery("SELECT r FROM Rental r WHERE r.customer.id = :customerId");
    query.setParameter("customerId", id);
    return query.getResultList();
  }

  @Override
  public List<Rental> getRentalsByStaffId(Long id) {
    TypedQuery<Rental> query = createQuery("SELECT r FROM Rental r WHERE r.staff_id = :staffId");
    query.setParameter("staffId", id);
    return query.getResultList();
  }

  @Override
  @Transactional
  public Rental insertRental(Rental rental) {
    entityManager.persist(rental);
    entityManager.flush();
    entityManager.refresh(rental);
    return rental;
  }

  private TypedQuery<Rental> createQuery(String sql) {
    return entityManager.createQuery(sql, Rental.class);
  }
}
