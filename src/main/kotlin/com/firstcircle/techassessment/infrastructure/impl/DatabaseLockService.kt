package com.firstcircle.techassessment.infrastructure.impl

import com.firstcircle.techassessment.infrastructure.LockService
import com.firstcircle.techassessment.infrastructure.repository.JpaAccountRepository
import org.springframework.stereotype.Component

@Component
class DatabaseLockService(
    private val jpaAccountRepository: JpaAccountRepository
) : LockService {

    override fun <T> withLock(id: Long, action: () -> T): T {
        jpaAccountRepository.findByIdWithLock(id)
        return action()
    }
}