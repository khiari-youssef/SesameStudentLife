package tn.sesame.spm.data.repositories.users

import kotlinx.coroutines.flow.Flow
import tn.sesame.spm.data.dataSources.UserPreferencesStore
import tn.sesame.spm.data.dataSources.UsersLocalDAO
import tn.sesame.spm.data.dataSources.UsersRemoteDAO
import tn.sesame.spm.data.toDomainModel
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.entities.SesameUserAccount

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

    override suspend fun clearUsersFromLocalStorage() {
        usersLocalDAO.deleteUsers()
    }

    override suspend fun getMyProfile(
        email: String,
        roleID : String
    ): SesameUser? = runCatching {
        usersLocalDAO.getLoggedInUserProfile(emailParam = email, roleIDParam = roleID)
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()

    override suspend fun getLoggedInUserAccount(): SesameUserAccount?  = runCatching {
        usersLocalDAO.getLoggedInUserAccount()
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()



}

