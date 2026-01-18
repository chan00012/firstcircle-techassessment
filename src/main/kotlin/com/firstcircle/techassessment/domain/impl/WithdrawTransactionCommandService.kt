package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.TransactionCommandService
import com.firstcircle.techassessment.domain.dto.WithdrawTransactionCreateInput
import com.firstcircle.techassessment.domain.model.Transaction
import com.firstcircle.techassessment.domain.model.WithdrawTransaction
import com.firstcircle.techassessment.infrastructure.AccountExistValidator
import com.firstcircle.techassessment.infrastructure.RemainingBalanceValidator
import com.firstcircle.techassessment.infrastructure.RemainingBalanceValidatorInput
import com.firstcircle.techassessment.infrastructure.repository.JpaTransactionRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class WithdrawTransactionCommandService(
    private val jpaTransactionRepository: JpaTransactionRepository,
    private val accountExistValidator: AccountExistValidator,
    private val remainingBalanceValidator: RemainingBalanceValidator
) : TransactionCommandService<WithdrawTransactionCreateInput> {
    override fun validate(input: WithdrawTransactionCreateInput) {
        accountExistValidator.validate(input.accountId)
        remainingBalanceValidator.validate(
            RemainingBalanceValidatorInput(
                accountId = input.accountId,
                amount = input.amount
            )
        )
    }

    override fun persist(input: WithdrawTransactionCreateInput): List<Transaction> {
        val withdrawTransaction = WithdrawTransaction(
            accountId = input.accountId,
            amount = input.amount,
            createdOn = Instant.now(),
        )

        val savedWithdrawTransaction = jpaTransactionRepository.save(withdrawTransaction.toJpa())
        return listOf(savedWithdrawTransaction.toDomain())
    }
}