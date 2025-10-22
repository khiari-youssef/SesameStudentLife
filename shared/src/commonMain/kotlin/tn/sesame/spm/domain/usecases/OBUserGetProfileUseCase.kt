package tn.sesame.spm.domain.usecases

import tn.sesame.spm.contracts.UseCaseContract
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface
import tn.sesame.spm.domain.entities.SesameUser

class OBUserGetProfileUseCase(
    private val usersRepository : UsersRepositoryInterface
) : UseCaseContract<String,SesameUser?> {


    override suspend fun execute(input : String) : SesameUser? = usersRepository.getMyProfile(id = input)

}
