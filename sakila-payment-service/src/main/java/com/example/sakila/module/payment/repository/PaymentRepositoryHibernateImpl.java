package com.example.sakila.module.payment.repository;

import com.example.sakila.module.payment.Payment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PaymentRepositoryHibernateImpl implements PaymentRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Payment getPaymentById(Long id) {
    return entityManager.find(Payment.class, id);
  }

  @Override
  public List<Payment> getPaymentsByRentalId(Long id) {
    TypedQuery<Payment> query = createQuery("SELECT p FROM Payment p WHERE p.rental.id = :rentalId");
    query.setParameter("rentalId", id);
    return query.getResultList();
  }

  @Override
  public List<Payment> getPaymentsByCustomerId(Long id) {
    TypedQuery<Payment> query = createQuery("SELECT p FROM Payment p WHERE p.customer.id = :customerId");
    query.setParameter("customerId", id);
    return query.getResultList();
  }

  @Override
  public List<Payment> getPaymentsByStaffId(Long id) {
    TypedQuery<Payment> query = createQuery("SELECT p FROM Payment p WHERE p.staff_id = :staffId");
    query.setParameter("staffId", id);
    return query.getResultList();
  }

  @Override
  @Transactional
  public Payment insertPayment(Payment payment) {
    entityManager.persist(payment);
    entityManager.flush();
    entityManager.refresh(payment);
    return payment;
  }

  @Override
  @Transactional
  public Payment updatePayment(Payment payment) {
    entityManager.merge(payment);
    entityManager.flush();
    entityManager.refresh(payment);
    return payment;
  }

  private TypedQuery<Payment> createQuery(String sql) {
    return entityManager.createQuery(sql, Payment.class);
  }
}
