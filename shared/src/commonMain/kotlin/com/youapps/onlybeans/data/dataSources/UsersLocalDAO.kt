package com.youapps.onlybeans.data.dataSources

import com.youapps.onlybeans.OnlyBeansDatabase
import com.youapps.onlybeans.data.dto.OBAddressDTO
import com.youapps.onlybeans.data.dto.OBUserProfileDTO
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.valueobjects.UserSex
import com.youapps.onlybeans.domain.valueobjects.decodeToUserSex
import io.ktor.util.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext


internal class UsersLocalDAO(
    private val onlyBeansDatabase : OnlyBeansDatabase,
    private val preferences : UserPreferencesStore
) {


    suspend fun saveUserData(
        token: String,
        obUser: OBUserProfileDTO
    ): Boolean = withContext(Dispatchers.IO) {
          onlyBeansDatabase.onlyBeansDatabaseQueries.run {
                transactionWithResult {
                    try {
                   onlyBeansDatabase.transaction {
                       insertOBUser(
                           firstName = obUser.firstName,
                           lastName = obUser.secondName,
                           email = obUser.email,
                           sex = obUser.sex,
                           phone = obUser.phone,
                           profilePicture = obUser.profilePicture,
                           status = obUser.status,
                           nationality = obUser.nationality,
                           address = obUser.address.toJson(),
                           coverPicture = obUser.coverPicture,
                           profileDescription = obUser.profileDescription
                       )
                   }

                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        rollback(false)
                    }
                    return@transactionWithResult true
                }.also { transactionCompleted ->
                    if (transactionCompleted) {
                        try {
                            preferences.setUserToken(
                                email = obUser.email,
                                token =token)
                        } catch (ex : Exception){
                            ex.printStackTrace()
                            return@withContext false
                        }
                    } else  return@withContext false
                }

              return@withContext true
          }
    }

    suspend fun getCurrentUserData(): OBUserProfile? = withContext(Dispatchers.IO) {
        val userEmail : String? = preferences.getUserEmail().firstOrNull()
        return@withContext  userEmail?.run {
             onlyBeansDatabase.onlyBeansDatabaseQueries.selectCurrentUserProfile(email = userEmail).executeAsOneOrNull()?.let { result->
                OBUserProfile(
                    firstName = result.firstName,
                    secondName = result.lastName,
                    email = result.email,
                    sex = result.sex?.decodeToUserSex(),
                    phone = result.phone,
                    profilePicture = result.profilePicture,
                    status = result.status,
                    nationality = result.nationality,
                    address = OBAddressDTO.fromJson(result.address)?.toDomainModel(),
                    profileDescription = result.profileDescription,
                    coverPicture = result.coverPicture,
                    myCoffeeSpace = null
                    )
            }
        }
    }





suspend fun deleteLoggedINUser() : Boolean{
    return withContext(Dispatchers.IO){
        onlyBeansDatabase.onlyBeansDatabaseQueries.run {
           return@run transactionWithResult {
                deleteOBUsers() > 0
            }
        }

    }
}




}