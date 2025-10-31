package com.youapps.users_management.ui.profile

import androidx.lifecycle.ViewModel
import com.youapps.onlybeans.contracts.UseCaseContractReadOnly
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MyProfileViewModel(
    private val oBUserGetProfileUseCase: UseCaseContractReadOnly<OBUserProfile?>,
    private val obUserLogoutUseCase: UseCaseContractReadOnly<Boolean>
) : ViewModel() {

  fun getMyProfile() : Flow<OBUserProfile?> = flow{
          val profile = oBUserGetProfileUseCase.execute()
          emit(profile)
  }.flowOn(Dispatchers.Main)

    fun logOutCurrentUser() : Flow<Boolean> = flow {
        emit(obUserLogoutUseCase.execute())
    }.flowOn(Dispatchers.Main)

}