package tn.sesame.spm.data.repositories.users

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import tn.sesame.spm.data.dataSources.UserPreferencesStore
import tn.sesame.spm.data.dataSources.UsersLocalDAO
import tn.sesame.spm.data.dataSources.UsersRemoteDAO
import tn.sesame.spm.domain.entities.SesameUser

internal class UsersRepository(
    private val usersLocalDAO: UsersLocalDAO,
    private val usersRemoteDAO: UsersRemoteDAO,
    private val userPreferencesStore: UserPreferencesStore
) : UsersRepositoryInterface {

    override suspend fun loginWithEmailAndPassword(email : String,password : String) : SesameUser {
        TODO("Not Implemented")
    }
    override suspend fun loginWithToken(token : String) : SesameUser {
        TODO("Not Implemented")
    }

    override suspend fun isAutoLoginEnabled(): Flow<Boolean?> = userPreferencesStore.isAutoLoginEnabled()

    override suspend fun setAutoLoginEnabled(isEnabled: Boolean){
        userPreferencesStore.setAutoLoginEnabled(isEnabled)
    }

}