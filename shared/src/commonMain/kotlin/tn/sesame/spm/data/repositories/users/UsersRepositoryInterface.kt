package tn.sesame.spm.data.repositories.users

import tn.sesame.spm.domain.entities.SesameUser

interface UsersRepositoryInterface {

    suspend fun loginWithEmailAndPassword(email : String,password : String) : SesameUser

    suspend fun loginWithToken(token : String) : SesameUser

}