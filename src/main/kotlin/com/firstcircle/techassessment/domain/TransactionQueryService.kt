package com.firstcircle.techassessment.domain

import com.firstcircle.techassessment.domain.model.Transaction

interface TransactionQueryService {
    fun getById(id: Long): Transaction

    fun getByAccountId(accountId: Long): List<Transaction>
}