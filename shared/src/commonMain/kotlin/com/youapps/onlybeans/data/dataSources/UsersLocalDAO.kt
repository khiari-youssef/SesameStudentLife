package com.youapps.onlybeans.data.dataSources

import com.youapps.onlybeans.OnlyBeansDatabase
import com.youapps.onlybeans.data.dto.OBAddressDTO
import com.youapps.onlybeans.data.dto.OBCoffeeCompanyDTO
import com.youapps.onlybeans.data.dto.OBCoffeeCompanyID
import com.youapps.onlybeans.data.dto.OBCoffeeFarmDTO
import com.youapps.onlybeans.data.dto.OBCoffeeFarmID
import com.youapps.onlybeans.data.dto.OBCoffeeShopDTO
import com.youapps.onlybeans.data.dto.OBCoffeeShopID
import com.youapps.onlybeans.data.dto.OBCoffeeSpaceDTO
import com.youapps.onlybeans.data.dto.OBHomeCoffeeBarDTO
import com.youapps.onlybeans.data.dto.OBHomeCoffeeBarID
import com.youapps.onlybeans.data.dto.OBUserProfileDTO
import com.youapps.onlybeans.di.fromDBJsonRow
import com.youapps.onlybeans.di.toDBJsonRow
import com.youapps.onlybeans.domain.entities.products.OBCoffeeSpace
import com.youapps.onlybeans.domain.entities.products.OBHomeCoffeeBar
import com.youapps.onlybeans.domain.entities.products.OBProductListItem
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.valueobjects.UserSex
import com.youapps.onlybeans.domain.valueobjects.decodeToUserSex
import io.ktor.util.logging.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json


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
                           profileDescription = obUser.profileDescription,
                           coffeeSpaceTypeID = obUser.myCoffeeSpace?.id
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
                 val myCoffeeSpace : OBCoffeeSpace? =   result.coffeeSpaceTypeID?.run {
                            when(result.coffeeSpaceTypeID) {
                         OBHomeCoffeeBarID -> {
                             val coffeeBarDTO = onlyBeansDatabase.onlyBeansDatabaseQueries.selectHomeCoffeeBar().executeAsOneOrNull()
                             coffeeBarDTO?.run {
                                 OBHomeCoffeeBar(
                                     spaceId = coffeeBarDTO.spaceId,
                                     userEmail = coffeeBarDTO.userEmail,
                                     coffeeGear = coffeeBarDTO.coffeeGear.runCatching {
                                         Json.decodeFromString<List<OBProductListItem>>(this)
                                     }.getOrNull() ?: emptyList(),
                                     coffeeBeans = coffeeBarDTO.coffeeBeans.runCatching {
                                         Json.decodeFromString<List<OBProductListItem>>(this)
                                     }.getOrNull() ?: emptyList(),
                                     gallery = coffeeBarDTO.gallery.runCatching {
                                         fromDBJsonRow()
                                     }.getOrNull(),
                                     description = coffeeBarDTO.description
                                 )
                             }
                         }
                         OBCoffeeShopID -> {
                             TODO()
                         }
                         OBCoffeeCompanyID -> {
                             TODO()
                         }
                         OBCoffeeFarmID -> {
                             TODO()
                         }
                         else ->  null
                     }
                 }

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
                    myCoffeeSpace = myCoffeeSpace
                    )
            }
        }
    }


    suspend fun saveCoffeeSpace(coffeeBarDTO: OBCoffeeSpaceDTO) : Boolean{
        return  when(coffeeBarDTO.id) {
            OBHomeCoffeeBarID -> {
               return runCatching {
                   coffeeBarDTO.data is OBHomeCoffeeBarDTO &&  onlyBeansDatabase.onlyBeansDatabaseQueries.insertHomeCoffeeBar(
                       spaceId = coffeeBarDTO.data.spaceId,
                       userEmail = coffeeBarDTO.data.userEmail,
                       description = coffeeBarDTO.data.description,
                       gallery = coffeeBarDTO.data.gallery.toDBJsonRow()!!,
                       coffeeBeans = coffeeBarDTO.data.coffeeBeans.run {
                           Json.encodeToString(this)
                       },
                       coffeeGear = coffeeBarDTO.data.coffeeGear.run {
                           Json.encodeToString(this)
                       }
                   ) > 0
               }.getOrNull() ?: false
            }
            OBCoffeeShopID -> {
                TODO()
            }
            OBCoffeeCompanyID -> {
                TODO()
            }
            OBCoffeeFarmID -> {
                TODO()
            }
            else ->  false
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