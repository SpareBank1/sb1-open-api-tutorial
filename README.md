# SpareBank1 Open API Tutorial

## Introduction
This application shows how one sets up a connection to the SpareBank1 API, getting an oauth token and retrieving 
data from production environment.
 
The example in this application sends a request to get a default account. Then the application sends another request to 
get all transaction for an account and print out the newest transactions.  

## Build Application
```` 
mvn clean install
````
  
## Set up for environment
### 1. Register Application
Go to [Personlig Klient](https://developer.sparebank1.no/personlig-klient) and choose the bank that you are a customer of
and register the application there. If you're not a customer in any of the SpareBank1 banks, then you need to create a customer 
relation here https://www.sparebank1.no. 

### 2. Get an authorization code
In the production environment, to get an oauth token you need an authorization code. To generate an authorization code 
open the following in a browser: 

    https://api-auth.sparebank1.no/oauth/authorize
    ?finInst=fid-smn
    &client_id=0f603d09-636f-4b3e-96fd-d56dc7d1a1a3
    &state=3138229
    &redirect_uri=https%3A%2F%2Fthisisyou.com
    &response_type=code

where

    finInst: is your bank's identifier. A list of banks ca can be found at https://developersparebank1.no/personlig-klient
    client_id: is the value issued to your application
    state: is any client-defined value
    redirect_uri: is where the client is redirected after authentication. Must match a pre-configured redirect URI for your application
    response_type: must be "code"
    
You will see a BANKID login screen. After the login, you will be redirected to specified redirect_uri with the 
authorization code.

**IMPORTANT**: Note that the authorization code is only valid for a couple of minutes and can only be used once to get
an oauth token. To get a new oauth token, you need to repeat Step 2 and issue a new authorization code. Even if a request 
to get an oauth token fails, you will still need to send a request to get a new authorization code.

### 3. Configure properties for production
Properties needed to run this application can be found [application.yaml](src/main/resources/application.yaml)

    api_url:  https://api.sparebank1.no
    client_id: found where you registered you application
    client_secret: found where you registered you application
    redirect_url: the same redirect url that you registered with your application
    authorization_code: Authorization code from BANKID login 
   
## Run the application
### From terminal
````
mvn spring-boot:run
````
### From IntelliJ IDEA
Run the application from the main function in [Sb1OpenApiTutorialApplication.kt](src/main/kotlin/sb1/developer/openapi/Sb1OpenApiTutorialApplication.kt)

### Oauth Token
Running the application will generate an oauth token used to make request to retrieve data. 

The oauth token is valid for 6 months and can be reused when making request to retrieve data. To make it simple, this 
application saves the token to a file called [token.txt](token.txt). When you rerun this application, the token is read
from the file. If you want to retest the request to get an oauth token, delete the value in [token.txt](token.txt). 
