package tn.sesame.spm.data.dataSources

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import tn.sesame.spm.data.toDTOString
import tn.sesame.spm.data.toEnumSex
import tn.sesame.spm.data.toSesameClass
import tn.sesame.spm.data.toSesameClasses
import tn.sesame.spm.domain.entities.SesameRole
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameTeacher
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.entities.SesameUserAccount
import tn.sesame.spmdatabase.SesameLogin
import tn.sesame.spmdatabase.SesameWorksLifeDatabase


internal class UsersLocalDAO(
    private val sesameWorksLifeDatabase : SesameWorksLifeDatabase
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

    suspend fun getLoggedInUserProfile(emailParam: String, roleIDParam: String): SesameUser? {
        return sesameWorksLifeDatabase.sesameWorksDatabaseQueries.run {
            transactionWithResult {
                return@transactionWithResult when (roleIDParam) {
                    "teacher_role" ->
                        selectTeacherProfileByEmail(emailParam).executeAsOneOrNull()?.run {
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
                                profBackground = profBackground ?: "",
                                role = SesameRole.getDefaultRoleForID(roleIDParam)
                            )
                        } ?: rollback(null)

                    "student_role" ->
                        selectStudentProfileByEmail(emailParam).executeAsOneOrNull()?.run {
                            SesameStudent(
                                registrationID = registrationID,
                                lastName = lastName ?: "",
                                firstName = firstName,
                                email = email,
                                sex = sex.toEnumSex(),
                                profilePicture = profile_picture_uri ?: "",
                                portfolioId = portfolio_id,
                                job = job,
                                sesameClass = sesameClass?.toSesameClass()
                                    ?: throw IllegalStateException(),
                                role = SesameRole.getDefaultRoleForID(roleIDParam)
                            )
                        } ?: rollback(null)

                    else -> rollback(null)
                }
            }
        }
    }


suspend fun deleteUsers() {
    withContext(Dispatchers.IO){
        sesameWorksLifeDatabase.sesameWorksDatabaseQueries.run {
            transaction {
                deleteLoginData()
                deleteTeachers()
                deleteStudents()
            }
        }
    }
}




}