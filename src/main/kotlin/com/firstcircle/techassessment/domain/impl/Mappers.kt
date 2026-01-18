package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.model.Amount
import com.firstcircle.techassessment.domain.model.DepositTransaction
import com.firstcircle.techassessment.domain.model.Transaction
import com.firstcircle.techassessment.domain.model.TransactionType
import com.firstcircle.techassessment.domain.model.TransferTransaction
import com.firstcircle.techassessment.domain.model.WithdrawTransaction
import com.firstcircle.techassessment.infrastructure.entity.JpaTransaction

fun JpaTransaction.toDomain(): Transaction {
    return when (transactionType) {
        TransactionType.DEPOSIT -> DepositTransaction(
            id = id,
            accountId = accountId,
            amount = Amount(amount, currency),
            createdOn = createdOn

        )

        TransactionType.WITHDRAWAL -> WithdrawTransaction(
            id = id,
            accountId = accountId,
            amount = Amount(amount, currency),
            createdOn = createdOn
        )

        TransactionType.TRANSFER -> TransferTransaction(
            id = id,
            accountId = accountId,
            transferAccountId = transferAccountId!!,
            amount = Amount(amount, currency),
            direction = direction,
            createdOn = createdOn
        )

        else -> throw IllegalArgumentException("Unsupported transaction type: $transactionType")
    }
}