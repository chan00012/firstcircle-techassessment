package com.firstcircle.techassessment.domain.dto

import com.firstcircle.techassessment.domain.model.Amount

sealed interface TransactionCreateInput {
    val accountId: Long
    val amount: Amount
}

data class DepositTransactionCreateInput(
    override val accountId: Long,
    override val amount: Amount
) : TransactionCreateInput

data class WithdrawTransactionCreateInput(
    override val accountId: Long,
    override val amount: Amount
) : TransactionCreateInput

data class TransferTransactionCreateInput(
    override val accountId: Long,
    override val amount: Amount,
    val transferAccountId: Long,
) : TransactionCreateInput
