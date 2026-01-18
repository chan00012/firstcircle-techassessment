package com.firstcircle.techassessment.domain

import com.firstcircle.techassessment.domain.dto.AccountCreationInput
import com.firstcircle.techassessment.domain.model.Account

interface AccountCommandService {
    fun create(input: AccountCreationInput): Account

}