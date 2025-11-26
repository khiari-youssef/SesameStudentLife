package com.youapps.onlybeans.domain.usecases

import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.valueobjects.OBAuthInterface


class OBUserLoginUseCase(
    private val usersRepository : OBUsersRepositoryInterface
) : UseCaseContract<OBAuthInterface, OBUserProfile> {


    override suspend fun execute(input: OBAuthInterface): OBUserProfile = when(input){
        is OBAuthInterface.OBTokenLogin -> {
            usersRepository.loginWithToken(input.value)
        }
        is OBAuthInterface.OBCredentialsLogin -> {
            usersRepository.loginWithEmailAndPassword(
                email = input.email,
                password = input.password
            )
        }
    }

}



