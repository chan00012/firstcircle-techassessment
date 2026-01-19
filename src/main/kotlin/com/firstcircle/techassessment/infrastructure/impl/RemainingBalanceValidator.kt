package com.firstcircle.techassessment.infrastructure.impl

import com.firstcircle.techassessment.domain.AccountQueryService
import com.firstcircle.techassessment.domain.Validator
import com.firstcircle.techassessment.domain.model.Amount
import org.springframework.stereotype.Component

@Component
class RemainingBalanceValidator(
    private val accountQueryService: AccountQueryService
) : Validator<RemainingBalanceValidatorInput> {

    override fun validate(input: RemainingBalanceValidatorInput) {
        require(accountQueryService.getAccountBalance(input.accountId) >= input.amount.value) {
            "Account with id ${input.accountId} does not have enough balance."
        }
    }
}

data class RemainingBalanceValidatorInput(
    val accountId: Long,
    val amount: Amount
)