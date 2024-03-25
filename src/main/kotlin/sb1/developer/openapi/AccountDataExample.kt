package sb1.developer.openapi

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import sb1.developer.openapi.dto.OauthDto
import sb1.developer.openapi.dto.TransactionsDto
import sb1.developer.openapi.dto.account.AccountDto
import java.io.File

private const val NUMBER_OF_TRANSACTIONS = 2

class AccountDataExample {
    private val configuration = RestClientConfiguration()
    private val restClient = RestClient(configuration)
    private val prettyObjectGson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
    private val prettyJsonGson = GsonBuilder().setPrettyPrinting().create()

    fun getAccountInformation() {
        val defaultResponse = restClient.getDefaultAccount(getOauthToken())
        println("Default account:")
        prettyPrintJson(defaultResponse)

        val defaultAccount = Gson().fromJson(defaultResponse, AccountDto::class.java)
        findNewestTransactions(defaultAccount.key)
    }

    fun findNewestTransactions(accountId: String) {
        val allTransaction = restClient.getAllTransaction(accountId, getOauthToken())
        val fromJson = Gson().fromJson(allTransaction, TransactionsDto::class.java)
        val newestTransactions = fromJson.list?.take(NUMBER_OF_TRANSACTIONS)
        if (!newestTransactions.isNullOrEmpty()) {
            println("Printing out $NUMBER_OF_TRANSACTIONS transactions:")
            newestTransactions.forEach { prettyPrintObject(it) }
        } else {
            print("No transactions found")
        }
    }

    /**
     * Retrieves a token from
     * If token is not present in the file then generate a new token by calling Sparebank1 API
     * @return oauth token
     */
    private fun getOauthToken(): String {
        File("token.txt").createNewFile()
        val tokenFromFile = File("token.txt").readText()
        return if (tokenFromFile.isNotEmpty()) {
            tokenFromFile
        } else {
            val accessTokenDto = Gson().fromJson(restClient.getToken(), OauthDto::class.java)
            File("token.txt").writeText(text = accessTokenDto.accessToken)
            println("Oauth token : ${accessTokenDto.accessToken}")
            accessTokenDto.accessToken
        }
    }

    private fun prettyPrintJson(saveRemaining: String?) {
        val jsonElement: JsonElement = JsonParser().parse(saveRemaining)
        println(prettyJsonGson.toJson(jsonElement))
    }

    private fun prettyPrintObject(obj: Any) {
        val toJson = prettyObjectGson.toJson(obj)
        println(toJson)
    }
}
