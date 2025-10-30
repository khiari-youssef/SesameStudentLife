package com.youapps.onlybeans.domain.usecases

import com.youapps.onlybeans.data.repositories.users.UsersRepositoryInterface
import com.youapps.onlybeans.contracts.UseCaseContractReadOnly

class OBUserLogoutUseCase(
    private val usersRepository : UsersRepositoryInterface
) : UseCaseContractReadOnly<Boolean>{

    override suspend fun execute() : Boolean {
       return usersRepository.clearUsersFromLocalStorage()
    }

}