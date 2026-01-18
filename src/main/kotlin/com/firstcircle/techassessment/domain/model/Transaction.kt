package com.firstcircle.techassessment.domain.model

import com.firstcircle.techassessment.infrastructure.entity.JpaTransaction
import java.time.Instant


sealed interface Transaction {
    val id: Long?
    val accountId: Long
    val amount: Amount
    val direction: Direction
    val transactionType: TransactionType
    val memo: String
    val createdOn: Instant

    fun toJpa(): JpaTransaction {
        return JpaTransaction(
            accountId = accountId,
            amount = amount.value,
            currency = amount.currency,
            direction = direction,
            transactionType = transactionType,
            memo = memo,
            createdOn = createdOn,
        )
    }
}

enum class TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER
}

enum class Direction {
    INCREASE,
    DECREASE
}