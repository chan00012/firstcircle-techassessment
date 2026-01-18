package com.firstcircle.techassessment.domain.model

import java.math.BigDecimal

data class Amount(
    val value: BigDecimal,
    val currency: CurrencyCode
)
