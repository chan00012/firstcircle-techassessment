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
    override fun validate(input: DepositTransactionCommand) {
        accountExistValidator.validate(input.accountId)
    }

    override fun persist(input: DepositTransactionCommand) {
        val depositTransaction = DepositTransaction(
            accountId = input.accountId,
            amount = input.amount,
            createdOn = Instant.now()
        )

        jpaTransactionRepository.save(depositTransaction.toJpa())
    }
}