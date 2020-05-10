package com.example.sakila.module.rental.repository

import com.example.sakila.module.rental.Rental
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class RentalRepositoryHibernateImpl : RentalRepository {

  @PersistenceContext
  private lateinit var entityManager: EntityManager

  override fun getRentalById(id: Long): Rental? = entityManager.find(Rental::class.java, id)

  override fun getRentalsByCustomerId(id: Long): List<Rental>? {
    val query = createQuery("SELECT r FROM Rental r WHERE r.customer.id = :customerId")
    query.setParameter("customerId", id)
    return query.resultList
  }

  override fun getRentalsByStaffId(id: Long): List<Rental>? {
    val query = createQuery("SELECT r FROM Rental r WHERE r.staffId = :staffId")
    query.setParameter("staffId", id)
    return query.resultList
  }

  @Transactional
  override fun insertRental(rental: Rental): Rental {
    entityManager.persist(rental)
    entityManager.flush()
    entityManager.refresh(rental)
    return rental
  }

  @Transactional
  override fun updateRental(rental: Rental): Rental {
    entityManager.merge(rental)
    entityManager.flush()
    entityManager.refresh(rental)
    return rental
  }

  @Transactional
  override fun deleteRental(rental: Rental) = entityManager.remove(rental)


  private fun createQuery(sql: String): TypedQuery<Rental> = entityManager.createQuery(sql, Rental::class.java)

}
