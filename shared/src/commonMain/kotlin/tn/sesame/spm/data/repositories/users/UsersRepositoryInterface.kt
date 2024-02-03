package tn.sesame.spm.data.repositories.users

import kotlinx.coroutines.flow.Flow
import tn.sesame.spm.domain.entities.SesameUser

interface UsersRepositoryInterface {

    suspend fun loginWithEmailAndPassword(email : String,password : String) : SesameUser

    suspend fun loginWithToken(token : String) : SesameUser

    suspend fun getLastSignedInUserToken() : String?
      fun isAutoLoginEnabled() : Flow<Boolean?>

    suspend fun setAutoLoginEnabled(isEnabled : Boolean)

}