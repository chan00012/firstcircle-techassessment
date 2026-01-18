package com.firstcircle.techassessment.domain.model

import java.time.Instant

data class DepositTransaction(
    override val id: Long? = null,
    override val accountId: Long,
    override val amount: Amount,
    override val createdOn: Instant,
) : Transaction {
    override val transactionType: TransactionType = TransactionType.DEPOSIT
    override val memo: String = "$transactionType : ${amount.value} ${amount.currency}"
    override val direction: Direction = Direction.INCREASE

    override fun toString(): String {
        return "DepositTransaction(id=$id, accountId=$accountId, memo='$memo', createdOn=$createdOn)"
    }
}