package com.youapps.users_management.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youapps.onlybeans.contracts.UseCaseContractReadOnly
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface
import com.youapps.onlybeans.domain.exception.DomainErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyProfileViewModel(
    private val usersRepository : OBUsersRepositoryInterface,
    private val obUserLogoutUseCase: UseCaseContractReadOnly<Boolean>
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileScreenState>(ProfileScreenState.Loading())
     val profileState : StateFlow<ProfileScreenState> = _profileState

    init {
        getMyProfile()
    }

  fun getMyProfile(withRefresh : Boolean = false) {
      viewModelScope.launch {
          _profileState.update { it->
              ProfileScreenState.Loading(withRefresh)
          }
          if (withRefresh) {
              val profile = usersRepository.getCurrentUserData(withRefresh = true)
              _profileState.update { it->
                  profile?.run {
                      ProfileScreenState.Loaded(profile)
                  } ?: run {
                      ProfileScreenState.Error(error = DomainErrorType.NotFound)
                  }
              }
          } else {
              val profile = usersRepository.getCurrentUserData()
               profile?.run {
                      _profileState.update { it->
                          ProfileScreenState.Loaded(profile)
                      }
                  } ?: run {
                      getMyProfile(withRefresh = true)
                  }
          }
      }
  }

    fun logOutCurrentUser() : Flow<Boolean> = flow {
        emit(obUserLogoutUseCase.execute())
    }.flowOn(Dispatchers.Main)

}