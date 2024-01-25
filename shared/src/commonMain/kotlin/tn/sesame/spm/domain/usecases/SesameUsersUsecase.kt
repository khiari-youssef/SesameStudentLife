package tn.sesame.spm.domain.usecases

import tn.sesame.spm.data.repositories.users.UsersRepository
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface
import tn.sesame.spm.domain.entities.SesameLoginInterface


class SesameUsersUsecase(
    private val usersRepository : UsersRepositoryInterface
) {


suspend fun loginUser(login : SesameLoginInterface) {
    when(login){
        is SesameLoginInterface.SesameTokenLogin -> {

        }
        is SesameLoginInterface.SesameCredentialsLogin -> {

        }
    }
}




}