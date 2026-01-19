package com.firstcircle.techassessment.domain.dto

import com.firstcircle.techassessment.domain.model.Amount

sealed interface TransactionCommand {
    val accountId: Long
    val amount: Amount
}

data class DepositTransactionCommand(
    override val accountId: Long,
    override val amount: Amount
) : TransactionCommand

data class WithdrawTransactionCommand(
    override val accountId: Long,
    override val amount: Amount
) : TransactionCommand

data class TransferTransactionCommand(
    override val accountId: Long,
    override val amount: Amount,
    val transferAccountId: Long,
) : TransactionCommand
