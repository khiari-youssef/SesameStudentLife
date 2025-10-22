package tn.sesame.spm.domain.usecases

import tn.sesame.spm.contracts.UseCaseContractReadOnly
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface

class OBUserLogoutUseCase(
    private val usersRepository : UsersRepositoryInterface
) : UseCaseContractReadOnly<Boolean>{

    override suspend fun execute() : Boolean {
       return usersRepository.clearUsersFromLocalStorage()
    }

}