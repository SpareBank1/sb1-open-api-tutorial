package sb1.developer.openapi.dto.account

import sb1.developer.openapi.dto.LinksDto
import sb1.developer.openapi.dto.MoneyDto

data class AccountDto(
        val key: String,
        val accountNumber: String,
        val name: String? = null,
        val description: String? = null,
        val balance: Number,
        val availableBalance: Number,
        val owner: CustomerDto,
        val product: String,
        val type: String,
        val interestRate: Number? = null,
        val freeWithdrawalsLeft: Int? = null,
        val _links: LinksDto? = null
)


