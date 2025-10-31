package com.youapps.onlybeans.domain.usecases

import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.onlybeans.contracts.UseCaseContractReadOnly
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface

import com.youapps.onlybeans.domain.entities.users.OBUserProfile

class OBUserGetProfileUseCase(
    private val usersRepository : OBUsersRepositoryInterface
) : UseCaseContractReadOnly<OBUserProfile?> {


    override suspend fun execute() : OBUserProfile? = usersRepository.getCurrentUserData()

}
