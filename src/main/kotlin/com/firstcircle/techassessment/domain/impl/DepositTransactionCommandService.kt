package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.LockableTransactionCommandService
import com.firstcircle.techassessment.domain.dto.DepositTransactionCommand
import com.firstcircle.techassessment.domain.model.DepositTransaction
import com.firstcircle.techassessment.infrastructure.LockService
import com.firstcircle.techassessment.infrastructure.impl.AccountExistValidator
import com.firstcircle.techassessment.infrastructure.repository.JpaTransactionRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DepositTransactionCommandService(
    private val jpaTransactionRepository: JpaTransactionRepository,
    private val accountExistValidator: AccountExistValidator,
    lockService: LockService
) : LockableTransactionCommandService<DepositTransactionCommand>(lockService) {
    override fun validate(command: DepositTransactionCommand) {
        accountExistValidator.validate(command.accountId)
    }

    override fun persist(command: DepositTransactionCommand) {
        val depositTransaction = DepositTransaction(
            accountId = command.accountId,
            amount = command.amount,
            createdOn = Instant.now()
        )

        jpaTransactionRepository.save(depositTransaction.toJpa())
    }
}