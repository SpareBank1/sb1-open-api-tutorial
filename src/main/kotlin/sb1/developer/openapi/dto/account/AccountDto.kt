package sb1.developer.openapi.dto.account

import sb1.developer.openapi.dto.LinksDto
import sb1.developer.openapi.dto.MoneyDto

data class AccountDto(
        val id: String,
        val accountNumber: AccountNumberDto,
        val name: String? = null,
        val description: String? = null,
        val balance: MoneyDto,
        val availableBalance: MoneyDto,
        val owner: CustomerDto,
        val product: String,
        val type: String,
        val interestRate: Number? = null,
        val freeWithdrawalsLeft: Int? = null,
        val _links: LinksDto? = null
)


