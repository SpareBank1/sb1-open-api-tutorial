package sb1.developer.openapi

import java.util.Properties

class RestClientConfiguration {
    val apiUrl: String
    val clientId: String
    val clientSecret: String
    val redirectUrl: String
    val authorizationCode: String

    companion object{
        val props = Properties()
    }

    init {
        props.load(RestClientConfiguration::class.java.classLoader.getResourceAsStream("application.yaml"))
        apiUrl = props.getProperty("api_url")
        clientId = props.getProperty("client_id")
        redirectUrl = props.getProperty("redirect_url")
        clientSecret = props.getProperty("client_secret")
        authorizationCode = props.getProperty("authorization_code").orEmpty()
    }
}