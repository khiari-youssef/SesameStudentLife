package com.youapps.onlybeans.domain.usecases

import com.youapps.onlybeans.data.repositories.users.UsersRepositoryInterface
import com.youapps.onlybeans.domain.entities.SesameUser
import com.youapps.onlybeans.contracts.UseCaseContract
class OBUserGetProfileUseCase(
    private val usersRepository : UsersRepositoryInterface
) : UseCaseContract<String, SesameUser?> {


    override suspend fun execute(input : String) : SesameUser? = usersRepository.getMyProfile(id = input)

}
