package com.firstcircle.techassessment.domain.model

import java.time.Instant

data class WithdrawTransaction(
    override val id: Long? = null,
    override val accountId: Long,
    override val amount: Amount,
    override val createdOn: Instant,
) : Transaction {
    override val transactionType: TransactionType = TransactionType.WITHDRAWAL
    override val memo: String = "$transactionType : -${amount.value} ${amount.currency}"
    override val direction: Direction = Direction.DECREASE

    override fun toString(): String {
        return "WithdrawTransaction(id=$id, accountId=$accountId, memo='$memo', createdOn=$createdOn)"
    }


}