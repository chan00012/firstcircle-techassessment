package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.LockableTransactionCommandService
import com.firstcircle.techassessment.domain.TransactionCommandService
import com.firstcircle.techassessment.domain.dto.TransferTransactionCommand
import com.firstcircle.techassessment.domain.model.Direction
import com.firstcircle.techassessment.domain.model.TransferTransaction
import com.firstcircle.techassessment.infrastructure.LockService
import com.firstcircle.techassessment.infrastructure.impl.AccountExistValidator
import com.firstcircle.techassessment.infrastructure.impl.RemainingBalanceValidator
import com.firstcircle.techassessment.infrastructure.impl.RemainingBalanceValidatorInput
import com.firstcircle.techassessment.infrastructure.repository.JpaTransactionRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class TransferTransactionCommandService(
    private val jpaTransactionRepository: JpaTransactionRepository,
    private val accountExistValidator: AccountExistValidator,
    private val remainingBalanceValidator: RemainingBalanceValidator,
    lockService: LockService
) : LockableTransactionCommandService<TransferTransactionCommand>(lockService) {
    override fun validate(input: TransferTransactionCommand) {
        accountExistValidator.validate(input.accountId)
        accountExistValidator.validate(input.transferAccountId)
        remainingBalanceValidator.validate(
            RemainingBalanceValidatorInput(
                accountId = input.accountId,
                amount = input.amount
            )
        )
    }

    override fun persist(input: TransferTransactionCommand) {
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

        jpaTransactionRepository.saveAll(listOf(sourceTransaction.toJpa(), destinationTransaction.toJpa()))
    }
}
