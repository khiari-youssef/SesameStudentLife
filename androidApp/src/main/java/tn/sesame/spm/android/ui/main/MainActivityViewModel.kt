package tn.sesame.spm.android.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tn.sesame.spm.data.repositories.users.UsersRepositoryInterface
import tn.sesame.spm.domain.usecases.SesameUsersUsecase



class MainActivityViewModel(
    private val usersRepositoryInterface: UsersRepositoryInterface
) : ViewModel() {

    fun autoLoginState()  {
       viewModelScope.launch {
           usersRepositoryInterface.isAutoLoginEnabled().collectLatest {isAutoLogin->
               if (isAutoLogin == true){
                   val token = usersRepositoryInterface.getLastSignedInUserToken()
                    token?.run {
                      val updatedUserData = usersRepositoryInterface.loginWithToken(token)
                        // check for exceptions
                    } ?: run {
                        // no existing user
                    }
               }
           }
       }
    }
}