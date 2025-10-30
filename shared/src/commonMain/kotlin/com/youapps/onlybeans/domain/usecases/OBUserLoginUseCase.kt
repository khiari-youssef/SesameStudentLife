package com.youapps.onlybeans.domain.usecases

import com.youapps.onlybeans.data.repositories.users.UsersRepositoryInterface
import com.youapps.onlybeans.domain.entities.SesameLoginInterface
import com.youapps.onlybeans.domain.entities.SesameUser
import com.youapps.onlybeans.contracts.UseCaseContract


class OBUserLoginUseCase(
    private val usersRepository : UsersRepositoryInterface
) : UseCaseContract<SesameLoginInterface, SesameUser> {


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



