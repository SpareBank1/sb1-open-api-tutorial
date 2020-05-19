package sb1.developer.openapi.dto

data class TransactionsDto(
        val transactions: List<TransactionDto>?,
        val _links: LinksDto? = null
)