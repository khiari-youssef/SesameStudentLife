package com.youapps.onlybeans.data.dataSources

import com.youapps.onlybeans.OnlyBeansDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import com.youapps.onlybeans.data.toDTOString
import com.youapps.onlybeans.data.toEnumSex
import com.youapps.onlybeans.data.toSesameClasses
import com.youapps.onlybeans.domain.entities.SesameStudent
import com.youapps.onlybeans.domain.entities.SesameTeacher
import com.youapps.onlybeans.domain.entities.SesameUser
import com.youapps.onlybeans.domain.entities.SesameUserAccount
import tn.sesame.spmdatabase.SesameLogin


internal class UsersLocalDAO(
    private val sesameWorksLifeDatabase : OnlyBeansDatabase
) {


    suspend fun saveUserData(
        sesameAuthToken: String,
        sesameUser: SesameUser
    ): Boolean = withContext(Dispatchers.IO) {
        sesameWorksLifeDatabase.sesameWorksDatabaseQueries
            .run {
                transactionWithResult {
                    try {
                        insertNewLogin(
                            token = sesameAuthToken,
                            role_id = sesameUser.role.id,
                            email = sesameUser.email
                        )
                        when (sesameUser) {
                            is SesameTeacher -> {
                                insertTeacherProfile(
                                    sesameUser.registrationID,
                                    sesameUser.firstName,
                                    sesameUser.lastName,
                                    sesameUser.email,
                                    sesameUser.sex.toDTOString(),
                                    sesameUser.profilePicture,
                                    sesameUser.profBackground,
                                    sesameUser.portfolioId,
                                    sesameUser.assignedClasses.toDTOString(),
                                )
                            }

                            is SesameStudent -> insertStudentProfile(
                                sesameUser.registrationID,
                                sesameUser.firstName,
                                sesameUser.lastName,
                                sesameUser.email,
                                sesameUser.sex.toDTOString(),
                                sesameUser.profilePicture,
                                sesameUser.portfolioId,
                                sesameUser.job,
                                sesameUser.sesameClass.toDTOString()
                            )

                            else -> rollback(false)
                        }
                    } catch (ex: Exception) {
                        rollback(false)
                    }
                    return@transactionWithResult true
                }
            }
    }

    suspend fun getLastUsedLogin(): SesameLogin? = withContext(Dispatchers.IO) {
        sesameWorksLifeDatabase.sesameWorksDatabaseQueries.selecteSavedLogin().executeAsOneOrNull()
    }

    suspend fun getLoggedInUserAccount(): SesameUserAccount {
        return sesameWorksLifeDatabase.sesameWorksDatabaseQueries.run {
            selecteSavedLogin().executeAsOneOrNull()?.let { savedLogin ->
                SesameUserAccount(
                    email = savedLogin.email,
                    role_id = savedLogin.role_id,
                    token = savedLogin.token
                )
            } ?: throw NoSuchElementException()
        }
    }

    suspend fun getUserProfileByID(id: String): SesameUser? {
        return sesameWorksLifeDatabase.sesameWorksDatabaseQueries.run {
            transactionWithResult {
                selectTeacherProfileByEmail(id).executeAsOneOrNull()?.run {
                    SesameTeacher(
                        registrationID = registrationID,
                        lastName = lastName ?: "",
                        firstName = firstName,
                        email = email,
                        sex = sex.toEnumSex(),
                        profilePicture = profile_picture_uri ?: "",
                        portfolioId = portfolio_id,
                        assignedClasses = assignedClassesID?.toSesameClasses()
                            ?.filterNotNull() ?: throw NoSuchElementException(),
                        profBackground = profBackground ?: ""
                    )
                } ?: rollback(null)
            }
        }
    }


suspend fun deleteUsers() : Boolean{
   return withContext(Dispatchers.IO){
        sesameWorksLifeDatabase.sesameWorksDatabaseQueries.run {
           return@run transactionWithResult {
                deleteLoginData() > 0
            }
        }
    }
}




}