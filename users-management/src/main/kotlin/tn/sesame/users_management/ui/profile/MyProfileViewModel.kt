package tn.sesame.users_management.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.usecases.SesameUsersUsecase

class MyProfileViewModel(
    private val usersUsecase: SesameUsersUsecase
) : ViewModel() {

  fun getMyProfile() : Flow<SesameUser?> = flow{
      val userAccount = usersUsecase.getLoggedInUserAccount()
      userAccount?.run {
          val profile = usersUsecase.getUserProfile(userAccount.email,userAccount.role_id)
          emit(profile)
      } ?: emit(null)
  }.flowOn(Dispatchers.Main)

   fun logout() {
       viewModelScope.launch {
           usersUsecase.logoutUser()
       }
   }
}