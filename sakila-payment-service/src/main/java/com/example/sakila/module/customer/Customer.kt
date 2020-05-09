package com.example.sakila.module.customer

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*
import java.time.OffsetDateTime

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "customer")
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    var id: Long? = null,

    @Column(name = "store_id")
    var storeId: Long? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "address_id")
    var addressId: Long? = null,

    @Column(name = "activebool")
    var activeBool: Boolean? = null,

    @Column(name = "create_date")
    var createDate: OffsetDateTime? = null,

    @Column(name = "last_update")
    var lastUpdate: OffsetDateTime? = null,

    @Column(name = "active")
    var active: Int? = null
) {

  constructor(customerId:Long?) : this(id=customerId)

}
