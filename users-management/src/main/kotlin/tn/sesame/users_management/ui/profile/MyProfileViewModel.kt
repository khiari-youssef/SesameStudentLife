package tn.sesame.users_management.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import tn.sesame.spm.contracts.UseCaseContract
import tn.sesame.spm.contracts.UseCaseContractReadOnly
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.usecases.OBUserGetProfileUseCase
import tn.sesame.spm.domain.usecases.OBUserLoginUseCase
import tn.sesame.spm.domain.usecases.OBUserLogoutUseCase

class MyProfileViewModel(
    private val oBUserGetProfileUseCase: UseCaseContract<String,SesameUser?>,
    private val usersRepositoryInterface: UsersRepositoryInterface,
    private val obUserLogoutUseCase: UseCaseContractReadOnly<Boolean>
) : ViewModel() {

  fun getMyProfile() : Flow<SesameUser?> = flow{
      val userAccount = usersRepositoryInterface.getLoggedInUserAccount()
      userAccount?.run {
          val profile = oBUserGetProfileUseCase.execute(userAccount.email)
          emit(profile)
      } ?: emit(null)
  }.flowOn(Dispatchers.Main)

    fun logOutCurrentUser() : Flow<Boolean> = flow {
        emit(obUserLogoutUseCase.execute())
    }.flowOn(Dispatchers.Main)

}