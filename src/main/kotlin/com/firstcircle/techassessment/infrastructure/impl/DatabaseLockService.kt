package com.firstcircle.techassessment.infrastructure.impl

import com.firstcircle.techassessment.infrastructure.LockService
import com.firstcircle.techassessment.infrastructure.repository.JpaAccountRepository
import org.springframework.stereotype.Component

@Component
class DatabaseLockService(
    private val jpaAccountRepository: JpaAccountRepository
) : LockService {
    override fun acquireLock(id: Long) {
        jpaAccountRepository.findByIdWithLock(id)
    }
}