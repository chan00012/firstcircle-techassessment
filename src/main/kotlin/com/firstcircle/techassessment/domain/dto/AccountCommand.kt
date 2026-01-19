package com.firstcircle.techassessment.domain.dto

import com.firstcircle.techassessment.domain.model.AccountType
import com.firstcircle.techassessment.domain.model.Amount

data class AccountCommand(
    val accountType: AccountType,
    val initialDeposit: Amount
)