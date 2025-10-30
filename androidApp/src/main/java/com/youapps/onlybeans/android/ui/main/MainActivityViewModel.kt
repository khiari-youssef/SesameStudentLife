package com.youapps.onlybeans.android.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.youapps.designsystem.R
import com.youapps.designsystem.components.bars.SesameBottomNavigationBarItem
import com.youapps.onlybeans.data.exceptions.CustomHttpException
import com.youapps.onlybeans.data.exceptions.HttpErrorType
import com.youapps.onlybeans.data.repositories.users.UsersRepositoryInterface
import com.youapps.onlybeans.domain.entities.SesameUser
import com.youapps.onlybeans.domain.exception.DomainErrorType
import com.youapps.onlybeans.domain.exception.DomainException
import com.youapps.onlybeans.security.BiometricAuthService
import com.youapps.onlybeans.security.SupportedDeviceAuthenticationMethods
import com.youapps.users_management.ui.login.LoginState


class MainActivityViewModel(
    private val usersRepositoryInterface: UsersRepositoryInterface,
    private val bioService : BiometricAuthService
) : ViewModel() {

    private val biometricCapabilitiesMutableState : MutableStateFlow<SupportedDeviceAuthenticationMethods>
            = MutableStateFlow(SupportedDeviceAuthenticationMethods.Waiting)
    val biometricCapabilitiesState : StateFlow<SupportedDeviceAuthenticationMethods> = biometricCapabilitiesMutableState

    private val autoLoginMutableState : MutableStateFlow<LoginState> = MutableStateFlow(
        LoginState.Loading
    )

    private val _navigationBarState : MutableStateFlow<MutableList<SesameBottomNavigationBarItem>> = MutableStateFlow(
        mutableListOf(
            SesameBottomNavigationBarItem(
                selectedStateIcon = R.drawable.ic_globe,
                unSelectedStateIcon = R.drawable.ic_globe
            ),
            SesameBottomNavigationBarItem(
                selectedStateIcon = R.drawable.ic_marketplace,
                unSelectedStateIcon = R.drawable.ic_marketplace
            ),
            SesameBottomNavigationBarItem(
                selectedStateIcon = R.drawable.ic_notifications,
                unSelectedStateIcon = R.drawable.ic_notifications_outlined,
                badgeContent = 0
            ),
            SesameBottomNavigationBarItem(
                selectedStateIcon = R.drawable.ic_profile,
                unSelectedStateIcon = R.drawable.ic_profile_outlined
            )
        ))

      val navigationBarState : StateFlow<List<SesameBottomNavigationBarItem>> = _navigationBarState

    val autoLoginState : StateFlow<LoginState>  = autoLoginMutableState

    init {
        checkBiometricCapabilitiesState()
        checkAutoLoginState()
    }


    fun checkBiometricCapabilitiesState(){
        biometricCapabilitiesMutableState.value = bioService.checkBiometricCapabilitiesState()
    }

      fun updateBadgeCount(itemIndex : Int,count : Int) {
          _navigationBarState.update { items->
              val badgeItem : SesameBottomNavigationBarItem? = items.getOrNull(itemIndex)?.copy(
                  badgeContent = count
              )
              badgeItem?.run {
                  items[itemIndex] = badgeItem
              }
              items
          }
    }

    private fun checkAutoLoginState() {
        viewModelScope.launch {
            usersRepositoryInterface.isAutoLoginEnabled()
                .map {isAutoLogin->
                    if (isAutoLogin == true){
                        val token = usersRepositoryInterface.getLastUsedLogin()
                        token?.run {
                            try {
                                val updatedUserData : SesameUser = usersRepositoryInterface.loginWithToken(token)
                                LoginState.Success(updatedUserData)
                            } catch (th: Throwable){
                                th.printStackTrace()
                                LoginState.Error(
                                    errorType = if (th is DomainException) th.errorType else DomainErrorType.Undefined
                                )
                            }
                        } ?: run {
                            usersRepositoryInterface.clearUsersFromLocalStorage()
                            LoginState.Idle
                        }
                    } else {
                        usersRepositoryInterface.clearUsersFromLocalStorage()
                        LoginState.Idle
                    }
                }.catch { th->
                    emit(
                        LoginState.Error(
                            errorType =  if (th is CustomHttpException){
                                if (th.errorType ==  HttpErrorType.UnauthorizedAccess) DomainErrorType.Unauthorized else DomainErrorType.Undefined
                            } else  DomainErrorType.Undefined
                        )
                    )
                }.collect(autoLoginMutableState)
        }
    }
}