package com.example.sakila.module.payment

import com.example.sakila.module.customer.Customer
import com.example.sakila.module.rental.Rental
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.time.OffsetDateTime
import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: Customer? = null,

    @Column(name = "staff_id")
    var staffId: Long? = null,

    @OneToOne
    @JoinColumn(name = "rental_id")
    var rental: Rental? = null,

    @Column(name = "amount")
    var amount: Float? = null,

    @Column(name = "payment_date")
    var paymentDate: OffsetDateTime? = null
) {

  constructor(paymentId: Long) : this(id = paymentId)

}
