package com.firstcircle.techassessment.domain

import com.firstcircle.techassessment.domain.dto.TransactionCommand
import com.firstcircle.techassessment.infrastructure.LockService
import org.springframework.transaction.annotation.Transactional

abstract class LockableTransactionCommandService<C : TransactionCommand>(
    private val lockService: LockService
) : TransactionCommandService<C> {

    @Transactional
    override fun create(command: C) {
        lockService.acquireLock(command.accountId)
        validate(command)
        persist(command)
    }
}