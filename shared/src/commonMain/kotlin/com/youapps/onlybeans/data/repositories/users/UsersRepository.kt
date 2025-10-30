package com.youapps.onlybeans.data.repositories.users

import com.youapps.onlybeans.data.dataSources.UsersLocalDAO
import com.youapps.onlybeans.data.dataSources.UsersRemoteDAO
import com.youapps.onlybeans.data.toDomainModel
import com.youapps.onlybeans.domain.entities.SesameUser
import kotlinx.coroutines.flow.Flow
import com.youapps.onlybeans.data.dataSources.UserPreferencesStore
import com.youapps.onlybeans.domain.entities.SesameUserAccount


internal class UsersRepository(
    private val usersLocalDAO: UsersLocalDAO,
    private val usersRemoteDAO: UsersRemoteDAO,
    private val userPreferencesStore: UserPreferencesStore
) : UsersRepositoryInterface {

    override suspend fun loginWithEmailAndPassword(email: String, password: String): SesameUser {
        return toDomainAuthenticationError(withCredentials = true) {
            val result = usersRemoteDAO.fetchEmailAndPasswordLoginAPI(email, password)
            val userData = result.data.toDomainModel()!!
            val hasTransactionSucceeded = usersLocalDAO.saveUserData(result.token, userData)
            if (hasTransactionSucceeded) result.data.toDomainModel()!! else throw IllegalStateException(
                "Local database transaction failed while saving user data!"
            )
        }
    }

    override suspend fun loginWithToken(token: String): SesameUser {
        return  toDomainAuthenticationError(withCredentials = false){
            val result = usersRemoteDAO.fetchTokenLoginAPI(token)
            val userData = result.data.toDomainModel()!!
            val hasTransactionSucceeded = usersLocalDAO.saveUserData(result.token, userData)
            if (hasTransactionSucceeded) result.data.toDomainModel()!! else throw IllegalStateException(
                "Local database transaction failed while saving user data!"
            )
        }
    }

    override fun isAutoLoginEnabled(): Flow<Boolean?> = userPreferencesStore.isAutoLoginEnabled()

    override suspend fun setAutoLoginEnabled(isEnabled: Boolean) {
        userPreferencesStore.setAutoLoginEnabled(isEnabled)
    }

    override suspend fun getLastUsedLogin(): String? =
        usersLocalDAO.getLastUsedLogin()?.token

    override suspend fun clearUsersFromLocalStorage() : Boolean {
        return usersLocalDAO.deleteUsers()
    }

    override suspend fun getMyProfile(
        id: String
    ): SesameUser? = runCatching {
        usersLocalDAO.getUserProfileByID(id)
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()

    override suspend fun getLoggedInUserAccount(): SesameUserAccount?  = runCatching {
        usersLocalDAO.getLoggedInUserAccount()
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()



}

