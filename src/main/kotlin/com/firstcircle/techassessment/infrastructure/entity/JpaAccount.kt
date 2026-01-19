package com.firstcircle.techassessment.infrastructure.entity

import com.firstcircle.techassessment.domain.model.Account
import com.firstcircle.techassessment.domain.model.AccountType
import com.firstcircle.techassessment.domain.model.CurrencyCode
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.Instant


@Entity
@Table(name = "account")
@SequenceGenerator(name = "account_seq_generator", sequenceName = "account_seq", allocationSize = 1)
class JpaAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_generator")
    val id: Long? = null,

    @Column(name = "account_number", nullable = false)
    val accountNumber: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    val accountType: AccountType,

    @Column(name = "currency", nullable = false)
    val currency: CurrencyCode,

    @Column(name = "created_on", nullable = false)
    val createdOn: Instant
)