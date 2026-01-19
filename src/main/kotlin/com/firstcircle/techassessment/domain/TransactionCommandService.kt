package com.firstcircle.techassessment.domain

import com.firstcircle.techassessment.domain.dto.TransactionCommand

interface TransactionCommandService<C : TransactionCommand> {
    fun create(command: C) {
        validate(command)
        return persist(command)
    }

    fun validate(command: C)

    fun persist(command: C)
}