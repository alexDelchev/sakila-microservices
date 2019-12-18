package com.example.sakila.module.staff.repository;

import com.example.sakila.module.staff.Staff;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class StaffRepositoryHibernateImpl implements StaffRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Staff getStaffById(Long id) {
    return entityManager.find(Staff.class, id);
  }

  @Override
  public List<Staff> getStaffByStoreId(Long storeId) {
    TypedQuery<Staff> query = createQuery("SELECT s FROM Staff s WHERE s.id = :storeId");
    query.setParameter("storeId", storeId);
    return query.getResultList();
  }

  @Override
  @Transactional
  public Staff insertStaff(Staff staff) {
    entityManager.persist(staff);
    entityManager.flush();
    entityManager.refresh(staff);
    return staff;
  }

  @Override
  @Transactional
  public Staff updateStaff(Staff staff) {
    entityManager.merge(staff);
    entityManager.flush();
    entityManager.refresh(staff);
    return staff;
  }

  private TypedQuery<Staff> createQuery(String query) {
    return entityManager.createQuery(query, Staff.class);
  }
}
