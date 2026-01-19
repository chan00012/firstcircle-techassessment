package com.firstcircle.techassessment.domain

import com.firstcircle.techassessment.domain.dto.TransactionCommand

interface TransactionCommandService<INPUT : TransactionCommand> {
    fun create(input: INPUT) {
        validate(input)
        return persist(input)
    }

    fun validate(input: INPUT)

    fun persist(input: INPUT)
}