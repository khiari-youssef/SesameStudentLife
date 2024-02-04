package tn.sesame.spm.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tn.sesame.spm.data.exceptions.CustomHttpException
import tn.sesame.spm.data.exceptions.HttpErrorType
import tn.sesame.spm.data.repositories.users.UsersRepository
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface
import tn.sesame.spm.domain.entities.SesameLoginInterface
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.entities.SesameUserAccount
import tn.sesame.spm.domain.exception.DomainErrorType
import tn.sesame.spm.domain.exception.DomainException


class SesameUsersUsecase(
    private val usersRepository : UsersRepositoryInterface
) {


suspend fun loginUser(login : SesameLoginInterface) : SesameUser = when(login){
    is SesameLoginInterface.SesameTokenLogin -> {
            usersRepository.loginWithToken(login.value)
    }
    is SesameLoginInterface.SesameCredentialsLogin -> {
            usersRepository.loginWithEmailAndPassword(
                email = login.email,
                password = login.password
            )
    }
}

fun checkIfAutoLoginIsEnabled() : Flow<Boolean> = usersRepository.isAutoLoginEnabled()
    .map { it ?: false }

suspend fun logoutUser(){
    usersRepository.clearUsersFromLocalStorage()
}

suspend fun getLoggedInUserAccount() : SesameUserAccount? = usersRepository.getLoggedInUserAccount()

suspend fun getUserProfile(email : String,roleID : String) : SesameUser?=
    usersRepository.getMyProfile(email, roleID)

}



