package com.youapps.onlybeans.data.repositories.users

import com.youapps.onlybeans.domain.entities.SesameUser
import com.youapps.onlybeans.domain.entities.SesameUserAccount
import kotlinx.coroutines.flow.Flow

interface UsersRepositoryInterface {

    suspend fun loginWithEmailAndPassword(email : String,password : String) : SesameUser

    suspend fun loginWithToken(token : String) : SesameUser

    suspend fun getLastUsedLogin() : String?
      fun isAutoLoginEnabled() : Flow<Boolean?>

    suspend fun setAutoLoginEnabled(isEnabled : Boolean)

    suspend fun clearUsersFromLocalStorage() : Boolean

    suspend fun getMyProfile(id: String) : SesameUser?

    suspend fun getLoggedInUserAccount() : SesameUserAccount?

}