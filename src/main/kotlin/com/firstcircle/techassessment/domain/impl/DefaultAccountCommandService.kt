package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.AccountCommandService
import com.firstcircle.techassessment.domain.dto.AccountCommand
import com.firstcircle.techassessment.domain.dto.DepositTransactionCommand
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
    override fun create(command: AccountCommand): Long {
        val jpaAccount = JpaAccount(
            accountNumber = generateAccountNumber(),
            accountType = command.accountType,
            currency = command.initialDeposit.currency,
            createdOn = Instant.now()
        )

        val savedAccount = jpaAccountRepository.save(jpaAccount)
        depositTransactionCommandService.create(
            DepositTransactionCommand(
                accountId = savedAccount.id!!,
                amount = command.initialDeposit
            )
        )

        return savedAccount.id!!
    }
}