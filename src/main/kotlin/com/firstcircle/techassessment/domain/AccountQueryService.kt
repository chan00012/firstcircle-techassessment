package com.firstcircle.techassessment.domain

import com.firstcircle.techassessment.domain.model.Account
import java.math.BigDecimal

interface AccountQueryService {
    fun getById(id: Long): Account?

    fun getAccountBalance(id: Long): BigDecimal
}