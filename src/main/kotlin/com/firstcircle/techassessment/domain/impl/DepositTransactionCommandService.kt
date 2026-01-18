package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.TransactionCommandService
import com.firstcircle.techassessment.domain.dto.DepositTransactionCreateInput
import com.firstcircle.techassessment.domain.model.DepositTransaction
import com.firstcircle.techassessment.domain.model.Transaction
import com.firstcircle.techassessment.infrastructure.AccountExistValidator
import com.firstcircle.techassessment.infrastructure.repository.JpaTransactionRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DepositTransactionCommandService(
    private val jpaTransactionRepository: JpaTransactionRepository,
    private val accountExistValidator: AccountExistValidator
) : TransactionCommandService<DepositTransactionCreateInput> {
    override fun validate(input: DepositTransactionCreateInput) {
        accountExistValidator.validate(input.accountId)
    }

    override fun persist(input: DepositTransactionCreateInput): List<Transaction> {
        val depositTransaction = DepositTransaction(
            accountId = input.accountId,
            amount = input.amount,
            createdOn = Instant.now()
        )

        val savedDepositTransaction = jpaTransactionRepository.save(depositTransaction.toJpa())
        return listOf(savedDepositTransaction.toDomain())
    }
}