package com.youapps.users_management.ui.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.onlybeans.contracts.UseCaseContractReadOnly
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface
import com.youapps.onlybeans.domain.entities.SesameUser

class MyProfileViewModel(
    private val oBUserGetProfileUseCase: UseCaseContract<String,SesameUser?>,
    private val OBUsersRepositoryInterface: OBUsersRepositoryInterface,
    private val obUserLogoutUseCase: UseCaseContractReadOnly<Boolean>
) : ViewModel() {

  fun getMyProfile() : Flow<SesameUser?> = flow{
      val userAccount = OBUsersRepositoryInterface.getLoggedInUserAccount()
      userAccount?.run {
          val profile = oBUserGetProfileUseCase.execute(userAccount.email)
          emit(profile)
      } ?: emit(null)
  }.flowOn(Dispatchers.Main)

    fun logOutCurrentUser() : Flow<Boolean> = flow {
        emit(obUserLogoutUseCase.execute())
    }.flowOn(Dispatchers.Main)

}