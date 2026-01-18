package com.firstcircle.techassessment.domain.model

import java.time.Instant

data class Account(
    val id: Long,
    val accountNumber: String,
    val accountType: AccountType,
    val currency: CurrencyCode,
    val createdOn: Instant
)


enum class AccountType {
    SAVINGS
}