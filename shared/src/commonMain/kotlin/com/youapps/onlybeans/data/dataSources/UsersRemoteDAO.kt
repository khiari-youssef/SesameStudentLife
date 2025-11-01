package com.youapps.onlybeans.data.dataSources

import com.youapps.onlybeans.data.dto.OBAddressDTO
import com.youapps.onlybeans.data.dto.OBLoginResponseWrapper
import com.youapps.onlybeans.data.dto.OBUserProfileDTO
import com.youapps.onlybeans.data.exceptions.CustomHttpException
import com.youapps.onlybeans.data.exceptions.HttpErrorType
import com.youapps.onlybeans.domain.entities.users.OBAddress
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

internal class UsersRemoteDAO(
    private val restClient : HttpClient
) {



    private val tokenLogins : Map<String,String> = mapOf(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InlvdXNzZWYua2hpYXJpQHNlc2FtZS5jb20udG4iLCJwYXNzd29yZCI6IjAwNzAwNyJ9.6a4KlE6CaP6NUyA1zDhPgHzQ7irJS5Y3MNw-RCEqzSM" to "khiari.youssef98@gmail.com",
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFtaXJhQHNlc2FtZS5jb20udG4iLCJwYXNzd29yZCI6IjExMTEifQ.tTN0z-dKcWXRtzheCKDSCv6zn5yjwNdvKGDoqddeTXI" to "kais@gmail.com"
    )



    suspend fun fetchEmailAndPasswordLoginAPI(
        email : String,password : String
    ) : OBLoginResponseWrapper = withContext(Dispatchers.IO){
         delay(500)
       if (email == "khiari.youssef98@gmail.com" && password == "0000") OBLoginResponseWrapper(
           data = OBUserProfileDTO(
             email = "khiari.youssef98@gmail.com",
             firstName = "Youssef",
             secondName = "Khiari",
             sex = "m",
             status = "Home barista",
              nationality = "Tunisian",
              address = OBAddressDTO(
                  country = "Tunisia",
                  city = "Tunis"
              ),
               phone = "25080060",
               profileDescription = "Nothing",
               profilePicture = "",
               coverPicture = "",
               myCoffeeSpace = null
           ),
           token = tokenLogins.keys.first()
       ) else throw CustomHttpException(errorType = HttpErrorType.UnauthorizedAccess)

    }

    suspend fun fetchTokenLoginAPI(
       token : String
    ) : OBLoginResponseWrapper  = withContext(Dispatchers.IO) {
        delay(300)

        if (token == tokenLogins.keys.first()) OBLoginResponseWrapper(
            data = OBUserProfileDTO(
                email = "khiari.youssef98@gmail.com",
                firstName = "Youssef",
                secondName = "Khiari",
                sex = "m",
                status = "Home barista",
                nationality = "Tunisian",
                address = OBAddressDTO(
                    country = "Tunisia",
                    city = "Tunis"
                ),
                phone = "25080060",
                profileDescription = "Nothing",
                profilePicture = "",
                coverPicture = "",
                myCoffeeSpace = null
            ) ,
            token = token
        ) else throw CustomHttpException(errorType = HttpErrorType.UnauthorizedAccess)
    }

}