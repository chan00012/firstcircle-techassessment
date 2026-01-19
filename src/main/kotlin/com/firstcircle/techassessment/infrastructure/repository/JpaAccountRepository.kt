package com.firstcircle.techassessment.infrastructure.repository

import com.firstcircle.techassessment.infrastructure.entity.JpaAccount
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface JpaAccountRepository : JpaRepository<JpaAccount, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM JpaAccount a WHERE a.id = :id")
    fun findByIdWithLock(id: Long): Optional<JpaAccount>
}