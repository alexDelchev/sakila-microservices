package com.example.sakila.module.rental

import com.example.sakila.module.customer.Customer
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "rental")
data class Rental(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    var id: Long? = null,

    @Column(name = "rental_date")
    var rentalDate: OffsetDateTime? = null,

    @Column(name = "film_id")
    var filmId: String? = null,

    @Column(name = "store_id")
    var storeId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null,

    @Column(name = "return_date")
    var returnDate: OffsetDateTime? = null,

    @Column(name = "staff_id")
    var staffId: Long? = null,

    @Column(name = "last_update")
    var lastUpdate: OffsetDateTime? = null
) {

  constructor(rentalId: Long?) : this(id = rentalId)

}
