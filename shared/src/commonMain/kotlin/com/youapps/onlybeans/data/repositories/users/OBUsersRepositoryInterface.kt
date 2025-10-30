package com.youapps.onlybeans.data.repositories.users

import com.youapps.onlybeans.domain.entities.SesameUser
import com.youapps.onlybeans.domain.entities.SesameUserAccount

interface OBUsersRepositoryInterface {

    suspend fun loginWithEmailAndPassword(email : String,password : String) : SesameUser

    suspend fun loginWithToken(token : String) : SesameUser

    suspend fun getActiveUserSession() : String?

    suspend fun clearUsersFromLocalStorage() : Boolean

    suspend fun getUserProfileByID(id: String) : SesameUser?

    suspend fun getLoggedInUserAccount() : SesameUserAccount?

}