package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.AccountQueryService
import com.firstcircle.techassessment.domain.TransactionQueryService
import com.firstcircle.techassessment.domain.model.Account
import com.firstcircle.techassessment.domain.model.Direction
import com.firstcircle.techassessment.infrastructure.repository.JpaAccountRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.jvm.optionals.getOrNull

@Service
class DefaultAccountQueryService(
    private val jpaAccountRepository: JpaAccountRepository,
    private val transactionQueryService: TransactionQueryService
) : AccountQueryService {

    override fun getById(id: Long): Account? {
        return jpaAccountRepository.findById(id).getOrNull()?.toDomain()

    }

    override fun getAccountBalance(id: Long): BigDecimal {
        val accountTransactions = transactionQueryService.getByAccountId(id)

        return accountTransactions.fold(BigDecimal.ZERO) { acc, transaction ->
            when (transaction.direction) {
                Direction.INCREASE -> acc + transaction.amount.value
                Direction.DECREASE -> acc - transaction.amount.value
            }
        }
    }
}