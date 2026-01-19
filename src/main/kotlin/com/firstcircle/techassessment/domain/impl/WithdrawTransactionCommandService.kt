package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.LockableTransactionCommandService
import com.firstcircle.techassessment.domain.dto.WithdrawTransactionCommand
import com.firstcircle.techassessment.domain.model.WithdrawTransaction
import com.firstcircle.techassessment.infrastructure.LockService
import com.firstcircle.techassessment.infrastructure.impl.AccountExistValidator
import com.firstcircle.techassessment.infrastructure.impl.RemainingBalanceValidator
import com.firstcircle.techassessment.infrastructure.impl.RemainingBalanceValidatorInput
import com.firstcircle.techassessment.infrastructure.repository.JpaTransactionRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class WithdrawTransactionCommandService(
    private val jpaTransactionRepository: JpaTransactionRepository,
    private val accountExistValidator: AccountExistValidator,
    private val remainingBalanceValidator: RemainingBalanceValidator,
    lockService: LockService
) : LockableTransactionCommandService<WithdrawTransactionCommand>(lockService) {
    override fun validate(command: WithdrawTransactionCommand) {
        accountExistValidator.validate(command.accountId)
        remainingBalanceValidator.validate(
            RemainingBalanceValidatorInput(
                accountId = command.accountId,
                amount = command.amount
            )
        )
    }

    override fun persist(command: WithdrawTransactionCommand) {
        val withdrawTransaction = WithdrawTransaction(
            accountId = command.accountId,
            amount = command.amount,
            createdOn = Instant.now(),
        )

        jpaTransactionRepository.save(withdrawTransaction.toJpa())
    }
}