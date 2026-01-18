package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.AccountCommandService
import com.firstcircle.techassessment.domain.dto.AccountCreationInput
import com.firstcircle.techassessment.domain.dto.DepositTransactionCreateInput
import com.firstcircle.techassessment.domain.model.Account
import com.firstcircle.techassessment.infrastructure.entity.JpaAccount
import com.firstcircle.techassessment.infrastructure.repository.JpaAccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class DefaultAccountCommandService(
    private val jpaAccountRepository: JpaAccountRepository,
    private val depositTransactionCommandService: DepositTransactionCommandService
) : AccountCommandService {

    companion object {
        private fun generateAccountNumber(): String =
            (1..10)
                .map { (0..9).random() }
                .joinToString("")

    }

    @Transactional
    override fun create(input: AccountCreationInput): Account {
        val jpaAccount = JpaAccount(
            accountNumber = generateAccountNumber(),
            accountType = input.accountType,
            currency = input.initialDeposit.currency,
            createdOn = Instant.now()
        )

        val savedAccount = jpaAccountRepository.save(jpaAccount)
        depositTransactionCommandService.create(
            DepositTransactionCreateInput(
                accountId = savedAccount.id!!,
                amount = input.initialDeposit
            )
        )

        return savedAccount.toDomain()
    }
}