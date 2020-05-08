package com.example.sakila.module.customer

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

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
    @Temporal(TemporalType.DATE)
    var createDate: Date? = null,

    @Column(name = "last_update")
    @Temporal(TemporalType.TIMESTAMP)
    var lastUpdate: Date? = null,

    @Column(name = "active")
    var active: Int? = null
) {

  constructor(customerId:Long?) : this(id=customerId)

}
