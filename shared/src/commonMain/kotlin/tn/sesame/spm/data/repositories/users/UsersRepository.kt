package tn.sesame.spm.data.repositories.users

import tn.sesame.spm.data.dataSources.UsersLocalDAO
import tn.sesame.spm.domain.entities.SesameUser

internal class UsersRepository(
    private val usersLocalDAO: UsersLocalDAO
) : UsersRepositoryInterface {

    override suspend fun loginWithEmailAndPassword(email : String,password : String) : SesameUser {
        TODO("Not Implemented")
    }
    override suspend fun loginWithToken(token : String) : SesameUser {
        TODO("Not Implemented")
    }

}