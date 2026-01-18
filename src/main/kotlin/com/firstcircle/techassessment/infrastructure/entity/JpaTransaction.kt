package com.firstcircle.techassessment.infrastructure.entity

import com.firstcircle.techassessment.domain.model.CurrencyCode
import com.firstcircle.techassessment.domain.model.Direction
import com.firstcircle.techassessment.domain.model.TransactionType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "transaction")
@SequenceGenerator(name = "transaction_seq_generator", sequenceName = "transaction_seq", allocationSize = 1)
data class JpaTransaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq_generator")
    val id: Long? = null,

    @Column(name = "account_id", nullable = false)
    val accountId: Long,

    @Column(name = "transfer_account_id", nullable = true)
    val transferAccountId: Long? = null,

    @Column(name = "amount", nullable = false)
    val amount: BigDecimal,

    @Column(name = "currency", nullable = false)
    val currency: CurrencyCode,

    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false)
    val direction: Direction,

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    val transactionType: TransactionType,

    @Column(name = "memo", nullable = false)
    val memo: String,

    @Column(name = "created_at", nullable = false)
    val createdOn: Instant
)
