package com.firstcircle.techassessment.infrastructure.repository

import com.firstcircle.techassessment.infrastructure.entity.JpaAccount
import org.springframework.data.jpa.repository.JpaRepository

interface JpaAccountRepository : JpaRepository<JpaAccount, Long> {
}