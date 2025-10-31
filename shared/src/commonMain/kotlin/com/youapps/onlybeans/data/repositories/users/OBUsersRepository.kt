package com.youapps.onlybeans.data.repositories.users

import com.youapps.onlybeans.data.dataSources.UserPreferencesStore
import com.youapps.onlybeans.data.dataSources.UsersLocalDAO
import com.youapps.onlybeans.data.dataSources.UsersRemoteDAO
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import kotlinx.coroutines.flow.firstOrNull


internal class OBUsersRepository(
    private val usersLocalDAO: UsersLocalDAO,
    private val usersRemoteDAO: UsersRemoteDAO,
    private val userPreferencesStore: UserPreferencesStore
) : OBUsersRepositoryInterface {

    override suspend fun loginWithEmailAndPassword(email: String, password: String): OBUserProfile {
        return toDomainAuthenticationError(withCredentials = true) {
            val result = usersRemoteDAO.fetchEmailAndPasswordLoginAPI(email, password)
            val hasTransactionSucceeded = usersLocalDAO.saveUserData(result.token, result.data)
            val userData = result.data.toDomainModel()
            if (hasTransactionSucceeded) userData else throw IllegalStateException(
                "Error while saving user data! in the device"
            )
        }
    }

    override suspend fun loginWithToken(token: String): OBUserProfile {
        return  toDomainAuthenticationError(withCredentials = false){
            val result = usersRemoteDAO.fetchTokenLoginAPI(token)
            val hasTransactionSucceeded = usersLocalDAO.saveUserData(result.token, result.data)
            val userData = result.data.toDomainModel()
            if (hasTransactionSucceeded) userData else throw IllegalStateException(
                "Error while saving user data! in the device"
            )
        }

    }


    override suspend fun getActiveUserSessionToken(): String? = userPreferencesStore.getUserToken().firstOrNull()


    override suspend fun clearUsersFromLocalStorage() : Boolean {
        return usersLocalDAO.deleteLoggedINUser()
    }

    override suspend fun getCurrentUserData(): OBUserProfile? = runCatching {
        usersLocalDAO.getCurrentUserData()
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()



}

