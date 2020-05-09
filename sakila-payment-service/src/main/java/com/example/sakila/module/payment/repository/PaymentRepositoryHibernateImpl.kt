package com.example.sakila.module.payment.repository

import com.example.sakila.module.payment.Payment
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class PaymentRepositoryHibernateImpl : PaymentRepository {

  @PersistenceContext
  private lateinit var entityManager: EntityManager

  override fun getPaymentById(id: Long): Payment = entityManager.find(Payment::class.java, id)

  override fun getPaymentsByRentalId(id: Long): List<Payment> {
    val query = createQuery("SELECT p FROM Payment p WHERE p.rental.id = :rentalId")
    query.setParameter("rentalId", id)
    return query.resultList
  }

  override fun getPaymentsByCustomerId(id: Long): List<Payment> {
    val query = createQuery("SELECT p FROM Payment p WHERE p.customer.id = :customerId")
    query.setParameter("customerId", id)
    return query.resultList
  }

  override fun getPaymentsByStaffId(id: Long): List<Payment> {
    val query = createQuery("SELECT p FROM Payment p WHERE p.staffId = :staffId")
    query.setParameter("staffId", id)
    return query.resultList
  }

  @Transactional
  override fun insertPayment(payment: Payment): Payment {
    entityManager.persist(payment)
    entityManager.flush()
    entityManager.refresh(payment)
    return payment
  }

  @Transactional
  override fun updatePayment(payment: Payment): Payment {
    entityManager.merge(payment)
    entityManager.flush()
    entityManager.refresh(payment)
    return payment
  }

  override fun deletePayment(payment: Payment) = entityManager.remove(payment)

  private fun createQuery(sql: String): TypedQuery<Payment> = entityManager.createQuery(sql, Payment::class.java)
}

