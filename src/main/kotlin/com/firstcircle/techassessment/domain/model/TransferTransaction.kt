package com.firstcircle.techassessment.domain.model

import com.firstcircle.techassessment.infrastructure.entity.JpaTransaction
import java.time.Instant

data class TransferTransaction(
    override val id: Long? = null,
    override val accountId: Long,
    override val amount: Amount,
    override val direction: Direction,
    override val createdOn: Instant,
    val transferAccountId: Long
) : Transaction {
    override val transactionType: TransactionType = TransactionType.TRANSFER
    override val memo: String = when (direction) {
        Direction.DECREASE -> "$transactionType to $transferAccountId : ${amount.value} ${amount.currency}"
        Direction.INCREASE -> "$transactionType from $accountId : ${amount.value} ${amount.currency}"
    }

    override fun toJpa(): JpaTransaction {
        return JpaTransaction(
            accountId = accountId,
            transferAccountId = transferAccountId,
            amount = amount.value,
            currency = amount.currency,
            direction = direction,
            transactionType = transactionType,
            memo = memo,
            createdOn = createdOn,
        )
    }

    override fun toString(): String {
        return "TransferTransaction(id=$id, accountId=$accountId, memo='$memo', createdOn=$createdOn)"
    }
}