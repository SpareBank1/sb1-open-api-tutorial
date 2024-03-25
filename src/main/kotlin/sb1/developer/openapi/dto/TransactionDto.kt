package sb1.developer.openapi.dto

data class TransactionDto(
        val valueDate: String,
        val amount: Number,
        val reference: String,
        val counterPartyAccountNumber: String? = null
)
