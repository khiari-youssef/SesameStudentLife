package tn.sesame.spm.domain.usecases

import tn.sesame.spm.contracts.UseCaseContract
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface
import tn.sesame.spm.domain.entities.SesameLoginInterface
import tn.sesame.spm.domain.entities.SesameUser


class OBUserLoginUseCase(
    private val usersRepository : UsersRepositoryInterface
) : UseCaseContract<SesameLoginInterface,SesameUser> {


    override suspend fun execute(input: SesameLoginInterface): SesameUser = when(input){
        is SesameLoginInterface.SesameTokenLogin -> {
            usersRepository.loginWithToken(input.value)
        }
        is SesameLoginInterface.SesameCredentialsLogin -> {
            usersRepository.loginWithEmailAndPassword(
                email = input.email,
                password = input.password
            )
        }
    }

}



