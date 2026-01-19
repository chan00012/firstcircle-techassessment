package com.firstcircle.techassessment

import com.firstcircle.techassessment.domain.AccountCommandService
import com.firstcircle.techassessment.domain.AccountQueryService
import com.firstcircle.techassessment.domain.dto.AccountCommand
import com.firstcircle.techassessment.domain.dto.DepositTransactionCommand
import com.firstcircle.techassessment.domain.dto.TransferTransactionCommand
import com.firstcircle.techassessment.domain.dto.WithdrawTransactionCommand
import com.firstcircle.techassessment.domain.impl.DefaultTransactionQueryService
import com.firstcircle.techassessment.domain.impl.DepositTransactionCommandService
import com.firstcircle.techassessment.domain.impl.TransferTransactionCommandService
import com.firstcircle.techassessment.domain.impl.WithdrawTransactionCommandService
import com.firstcircle.techassessment.domain.model.AccountType
import com.firstcircle.techassessment.domain.model.Amount
import com.firstcircle.techassessment.domain.model.CurrencyCode
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal

@SpringBootTest
class IntegrationTests {
    companion object {
        val logger = KotlinLogging.logger {}
    }

    @Autowired
    private lateinit var accountCommandService: AccountCommandService

    @Autowired
    private lateinit var accountQueryService: AccountQueryService

    @Autowired
    private lateinit var depositTransactionCommandService: DepositTransactionCommandService

    @Autowired
    private lateinit var withdrawTransactionCommandService: WithdrawTransactionCommandService

    @Autowired
    private lateinit var transferTransactionCommandService: TransferTransactionCommandService

    @Autowired
    private lateinit var transactionQueryService: DefaultTransactionQueryService

    @Test
    fun `vanilla flow`() {
        val firstAccount = accountCommandService.create(
            AccountCommand(
                accountType = AccountType.SAVINGS,
                initialDeposit = Amount(
                    value = BigDecimal.valueOf(100),
                    currency = CurrencyCode.PHP
                )
            )
        )

        val secondAccount = accountCommandService.create(
            AccountCommand(
                accountType = AccountType.SAVINGS,
                initialDeposit = Amount(
                    value = BigDecimal.valueOf(100),
                    currency = CurrencyCode.PHP
                )
            )
        )

        logger.info { "${accountQueryService.getById(firstAccount)} was created" }
        logger.info { "${accountQueryService.getById(firstAccount)} was created" }

        depositTransactionCommandService.create(
            DepositTransactionCommand(
                accountId = firstAccount,
                amount = Amount(
                    value = BigDecimal.valueOf(100),
                    currency = CurrencyCode.PHP
                )
            )
        )

        logger.info { "account $firstAccount current balance: ${accountQueryService.getAccountBalance(firstAccount)}" }
        logger.info { "account $secondAccount current balance: ${accountQueryService.getAccountBalance(secondAccount)}" }
        logger.info { "---" }

        withdrawTransactionCommandService.create(
            WithdrawTransactionCommand(
                accountId = firstAccount,
                amount = Amount(
                    value = BigDecimal.valueOf(99),
                    currency = CurrencyCode.PHP
                )
            )
        )

        logger.info { "account $firstAccount current balance: ${accountQueryService.getAccountBalance(firstAccount)}" }
        logger.info { "account $secondAccount current balance: ${accountQueryService.getAccountBalance(secondAccount)}" }
        logger.info { "---" }

        transferTransactionCommandService.create(
            TransferTransactionCommand(
                accountId = firstAccount,
                transferAccountId = secondAccount,
                amount = Amount(
                    value = BigDecimal.valueOf(1),
                    currency = CurrencyCode.PHP
                )
            )
        )

        logger.info { "account $firstAccount current balance: ${accountQueryService.getAccountBalance(firstAccount)}" }
        logger.info { "account $secondAccount current balance: ${accountQueryService.getAccountBalance(secondAccount)}" }
        logger.info { "---" }

        val firstAccountBalance = accountQueryService.getAccountBalance(firstAccount)
        val secondAccountBalance = accountQueryService.getAccountBalance(secondAccount)

        assertThat(firstAccountBalance).isEqualTo(BigDecimal("100.00"))
        assertThat(secondAccountBalance).isEqualTo(BigDecimal("101.00"))
    }

    @Test
    fun `concurrent requests`(): Unit = runBlocking {
        val account = accountCommandService.create(
            AccountCommand(
                accountType = AccountType.SAVINGS,
                initialDeposit = Amount(
                    value = BigDecimal.valueOf(100),
                    currency = CurrencyCode.PHP
                )
            )
        )

        val results = (1..10).map {
            async {
                runCatching {
                    withdrawTransactionCommandService.create(
                        WithdrawTransactionCommand(
                            accountId = account,
                            amount = Amount(BigDecimal.valueOf(100), CurrencyCode.PHP)
                        )
                    )
                    logger.info { "Withdraw succeeded" }
                }
            }
        }.awaitAll()

        logger.info { "account $account current balance: ${accountQueryService.getAccountBalance(account)}" }

        val success = results.count { it.isSuccess }
        val failures = results.count { it.isFailure }

        assertThat(success).isEqualTo(1)
        assertThat(failures).isEqualTo(9)
    }
}