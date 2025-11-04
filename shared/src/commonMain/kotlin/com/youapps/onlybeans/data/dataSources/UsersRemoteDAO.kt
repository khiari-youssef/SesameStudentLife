package com.youapps.onlybeans.data.dataSources

import com.youapps.onlybeans.data.dto.OBAddressDTO
import com.youapps.onlybeans.data.dto.OBCoffeeSpaceDTO
import com.youapps.onlybeans.data.dto.OBHomeCoffeeBarDTO
import com.youapps.onlybeans.data.dto.OBHomeCoffeeBarID
import com.youapps.onlybeans.data.dto.OBLoginResponseWrapper
import com.youapps.onlybeans.data.dto.OBUserProfileDTO
import com.youapps.onlybeans.data.exceptions.CustomHttpException
import com.youapps.onlybeans.data.exceptions.HttpErrorType
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

    private val _mockUser = OBUserProfileDTO(
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
        profileDescription = "aaaaaaaa".repeat(12).repeat(4),
        coverPicture = "https://images.unsplash.com/photo-1601813913455-118810e79277?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1170",
        profilePicture = "https://avatar.iran.liara.run/public",
        myCoffeeSpace = OBCoffeeSpaceDTO(
              id = OBHomeCoffeeBarID,
            data = OBHomeCoffeeBarDTO(
                spaceId = "OBHomeCoffeeBarID",
                userEmail = "khiari.youssef98@gmail.com",
                description = "",
                gallery = listOf(
                    "https://images.unsplash.com/photo-1572982270699-473dfa34d7e7?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1170",
                    "https://images.unsplash.com/photo-1522126039546-182129aa0b93?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1331",
                    "https://images.unsplash.com/photo-1610889556528-9a770e32642f?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1315",
                    "https://images.unsplash.com/photo-1581068106019-5aa70c6ab424?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&q=80&w=1171"
                ),
                coffeeGear = listOf(),
                coffeeBeans = listOf()
            )
        )
    )



    suspend fun fetchEmailAndPasswordLoginAPI(
        email : String,password : String
    ) : OBLoginResponseWrapper = withContext(Dispatchers.IO){
         delay(500)
       if (email == "khiari.youssef98@gmail.com" && password == "0000") OBLoginResponseWrapper(
           data = _mockUser,
           token = tokenLogins.keys.first()
       ) else throw CustomHttpException(errorType = HttpErrorType.UnauthorizedAccess)

    }

    suspend fun fetchTokenLoginAPI(
       token : String
    ) : OBLoginResponseWrapper  = withContext(Dispatchers.IO) {
        delay(300)

        if (token == tokenLogins.keys.first()) OBLoginResponseWrapper(
            data = _mockUser ,
            token = token
        ) else throw CustomHttpException(errorType = HttpErrorType.UnauthorizedAccess)
    }

    suspend fun fetchUserProfileData(token : String) : OBUserProfileDTO? {
        delay(300)
        return  _mockUser
    }

}