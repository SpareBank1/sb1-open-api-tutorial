package sb1.developer.openapi

import kong.unirest.HttpResponse
import kong.unirest.Unirest
import kong.unirest.Unirest.get
import org.apache.http.HttpStatus
import java.net.URLEncoder

class RestClient(private val configuration: RestClientConfiguration) {

    fun getToken(): String {
        print("Sending Request to /oauth/token ")
        val response = Unirest.post(configuration.authUrl + "/oauth/token")
                .header("Cache-Control", "no-cache")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", getGrantType())
                .field("redirect_url", configuration.redirectUrl) // only used in production
                .field("code", configuration.authorizationCode) // only used in production
                .field("client_id", configuration.clientId)
                .field("client_secret", configuration.clientSecret)
                .asString()
        return parseResponse(response)
    }

    private fun getGrantType(): String {
        return if (!configuration.authorizationCode.isBlank()) {
            "authorization_code" //Used in Production
        } else {
            "client_credentials" //Used in Sandbox
        }
    }

    fun getDefaultAccount(oauthToken: String): String {
        print("Sending Request to /personal/banking/accounts/default ")
        val response = get(configuration.apiUrl + "/personal/banking/accounts/default")
                .header("Authorization", "Bearer $oauthToken")
                .header("Cache-Control", "no-cache")
                .header("Content-Type", "application/vnd.sparebank1.v1+json")
                .asString()

        return parseResponse(response)
    }

    fun getAllTransaction(accountId: String, oauthToken: String): String {
        print("Sending Request to ${configuration.apiUrl}/personal/banking/transactions?accountKey=$accountId")
        val encodedAccountId = URLEncoder.encode(accountId, "UTF-8")
        val response = get("${configuration.apiUrl}/personal/banking/transactions")
                .header("Authorization", "Bearer $oauthToken")
                .header("Cache-Control", "no-cache")
                .header("Content-Type", "application/vnd.sparebank1.v1+json")
                .queryString("accountKey", encodedAccountId)
                .asString()
        return parseResponse(response)
    }

    private fun parseResponse(response: HttpResponse<String>): String {
        println("Response status ${response.status} " + response.statusText)
        if (response.status == HttpStatus.SC_OK) {
            return response.body
        } else {
            throw Exception(response.body)
        }
    }

}