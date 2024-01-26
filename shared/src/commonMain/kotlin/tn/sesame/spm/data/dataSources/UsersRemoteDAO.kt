package tn.sesame.spm.data.dataSources

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

class UsersRemoteDAO(
    private val restClient : HttpClient
) {

    suspend fun fetchEmailAndPasswordLoginAPI(
        email : String,password : String
    ) {
        restClient.post("/login/v1") {
            setBody(mapOf(
                "email" to email,
                "password" to password
            ))
        }
    }

    suspend fun fetchTokenLoginAPI(
       token : String
    ) : Boolean? {
       return restClient.get("/login/v1") {
           header(HttpHeaders.Authorization,"Bearer $token")
       }.body<Map<String,Boolean>>().get("isAuthorized")
    }

}