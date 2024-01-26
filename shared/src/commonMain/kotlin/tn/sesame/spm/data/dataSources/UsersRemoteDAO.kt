package tn.sesame.spm.data.dataSources

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tn.sesame.spm.data.dto.SesameUserDTO

class UsersRemoteDAO(
    private val restClient : HttpClient
) {


    suspend fun fetchEmailAndPasswordLoginAPI(
        email : String,password : String
    ) : SesameUserDTO = withContext(Dispatchers.IO){
      val response =  restClient.post("/login/v1") {
            setBody(mapOf(
                "email" to email,
                "password" to password
            ))
        }
      val body = response.body<SesameUserDTO>()
         body
    }

    suspend fun fetchTokenLoginAPI(
       token : String
    ) : Boolean?  = withContext(Dispatchers.IO) {
        restClient.get("/login/v1") {
           header(HttpHeaders.Authorization,"Bearer $token")
       }.body<Map<String,Boolean>>()["isAuthorized"]
    }

}