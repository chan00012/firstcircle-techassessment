package com.firstcircle.techassessment.domain

import com.firstcircle.techassessment.domain.dto.AccountCommand

interface AccountCommandService {
    fun create(command: AccountCommand): Long

}