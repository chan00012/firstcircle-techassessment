package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.TransactionCommandService
import com.firstcircle.techassessment.domain.dto.TransferTransactionCreateInput
import com.firstcircle.techassessment.domain.model.Direction
import com.firstcircle.techassessment.domain.model.Transaction
import com.firstcircle.techassessment.domain.model.TransferTransaction
import com.firstcircle.techassessment.infrastructure.AccountExistValidator
import com.firstcircle.techassessment.infrastructure.RemainingBalanceValidator
import com.firstcircle.techassessment.infrastructure.RemainingBalanceValidatorInput
import com.firstcircle.techassessment.infrastructure.repository.JpaTransactionRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TransferTransactionCommandService(
    private val jpaTransactionRepository: JpaTransactionRepository,
    private val accountExistValidator: AccountExistValidator,
    private val remainingBalanceValidator: RemainingBalanceValidator
) : TransactionCommandService<TransferTransactionCreateInput> {
    override fun validate(input: TransferTransactionCreateInput) {
        accountExistValidator.validate(input.accountId)
        accountExistValidator.validate(input.transferAccountId)
        remainingBalanceValidator.validate(
            RemainingBalanceValidatorInput(
                accountId = input.accountId,
                amount = input.amount
            )
        )
    }

    override fun persist(input: TransferTransactionCreateInput): List<Transaction> {
        val sourceTransaction = TransferTransaction(
            accountId = input.accountId,
            transferAccountId = input.transferAccountId,
            amount = input.amount,
            direction = Direction.DECREASE,
            createdOn = Instant.now(),
        )

        val destinationTransaction = TransferTransaction(
            accountId = input.transferAccountId,
            transferAccountId = input.accountId,
            amount = input.amount,
            direction = Direction.INCREASE,
            createdOn = Instant.now(),
        )

        val savedTransactions =
            jpaTransactionRepository.saveAll(listOf(sourceTransaction.toJpa(), destinationTransaction.toJpa()))
        return savedTransactions.map { it.toDomain() }
    }
}
