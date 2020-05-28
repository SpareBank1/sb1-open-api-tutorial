package sb1.developer.openapi.dto

data class TransactionDto(
        val amount: MoneyDto,
        val accountingDate: String,
        val description: String? = null,
        val fullDescription: String? = null,
        val archiveReference: String? = null,
        val remoteAccount: String? = null,
        val transactionCode: String? = null,
        val transactionType: String? = null,
        val _links: LinksDto? = null
)
