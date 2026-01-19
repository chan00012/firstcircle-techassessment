package com.firstcircle.techassessment.domain

import com.firstcircle.techassessment.domain.dto.TransactionCommand
import com.firstcircle.techassessment.infrastructure.LockService
import org.springframework.transaction.annotation.Transactional

abstract class LockableTransactionCommandService<INPUT : TransactionCommand>(
    private val lockService: LockService
) : TransactionCommandService<INPUT> {

    @Transactional
    override fun create(input: INPUT) {
        lockService.acquireLock(input.accountId)
        validate(input)
        persist(input)
    }
}