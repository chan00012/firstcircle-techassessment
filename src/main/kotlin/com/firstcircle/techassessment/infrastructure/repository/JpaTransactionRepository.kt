package com.firstcircle.techassessment.infrastructure.repository

import com.firstcircle.techassessment.infrastructure.entity.JpaTransaction
import org.springframework.data.jpa.repository.JpaRepository

interface JpaTransactionRepository : JpaRepository<JpaTransaction, Long> {

    fun findByAccountId(accountId: Long): List<JpaTransaction>
}