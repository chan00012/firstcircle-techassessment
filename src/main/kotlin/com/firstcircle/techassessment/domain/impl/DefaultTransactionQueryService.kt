package com.firstcircle.techassessment.domain.impl

import com.firstcircle.techassessment.domain.TransactionQueryService
import com.firstcircle.techassessment.domain.model.Transaction
import com.firstcircle.techassessment.infrastructure.repository.JpaTransactionRepository
import org.springframework.stereotype.Service

@Service
class DefaultTransactionQueryService(
    private val jpaTransactionRepository: JpaTransactionRepository
) : TransactionQueryService {
    override fun getById(id: Long): Transaction {
        return jpaTransactionRepository.findById(id)
            .orElseThrow { error("Transaction $id not found.") }
            .toDomain()
    }

    override fun getByAccountId(accountId: Long): List<Transaction> {
        return jpaTransactionRepository.findByAccountId(accountId)
            .map { it.toDomain() }
    }
}