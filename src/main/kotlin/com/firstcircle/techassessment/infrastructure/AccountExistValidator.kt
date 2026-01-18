package com.firstcircle.techassessment.infrastructure

import com.firstcircle.techassessment.domain.AccountQueryService
import com.firstcircle.techassessment.domain.Validator
import org.springframework.stereotype.Component

@Component
class AccountExistValidator(
    private val accountQueryService: AccountQueryService
) : Validator<Long> {

    override fun validate(input: Long) {
        requireNotNull(accountQueryService.getById(input)) {
            "Account with id $input does not exist."
        }
    }
}