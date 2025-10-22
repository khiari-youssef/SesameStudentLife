package tn.sesame.spm.data.repositories.users

import kotlinx.coroutines.flow.Flow
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.entities.SesameUserAccount

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