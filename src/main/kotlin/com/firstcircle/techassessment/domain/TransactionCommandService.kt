package com.firstcircle.techassessment.domain

import com.firstcircle.techassessment.domain.dto.TransactionCreateInput
import com.firstcircle.techassessment.domain.model.Transaction

interface TransactionCommandService<INPUT : TransactionCreateInput> {
    fun create(input: INPUT): List<Transaction> {
        validate(input)
        return persist(input)
    }

    fun validate(input: INPUT)

    fun persist(input: INPUT): List<Transaction>
}