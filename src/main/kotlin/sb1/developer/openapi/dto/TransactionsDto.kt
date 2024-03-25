package sb1.developer.openapi.dto

data class TransactionsDto(
        val list: List<TransactionDto>?,
        val _links: LinksDto? = null
)