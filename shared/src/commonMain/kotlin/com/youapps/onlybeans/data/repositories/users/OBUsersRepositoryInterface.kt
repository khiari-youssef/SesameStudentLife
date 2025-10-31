package com.youapps.onlybeans.data.repositories.users


import com.youapps.onlybeans.domain.entities.users.OBUserProfile

interface OBUsersRepositoryInterface {

    suspend fun loginWithEmailAndPassword(email : String,password : String) : OBUserProfile

    suspend fun loginWithToken(token : String) : OBUserProfile

    suspend fun getActiveUserSessionToken() : String?

    suspend fun clearUsersFromLocalStorage() : Boolean

    suspend fun getCurrentUserData() : OBUserProfile?


}