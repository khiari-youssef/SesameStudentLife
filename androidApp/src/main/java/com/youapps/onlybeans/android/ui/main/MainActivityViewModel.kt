package com.youapps.onlybeans.android.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youapps.designsystem.R
import com.youapps.designsystem.components.bars.OBBottomNavigationBarItem
import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.valueobjects.OBAuthInterface
import com.youapps.onlybeans.security.BiometricAuthService
import com.youapps.onlybeans.security.SupportedDeviceAuthenticationMethods
import com.youapps.users_management.ui.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainActivityViewModel(
    private val oBUsersRepositoryInterface: OBUsersRepositoryInterface,
    private val oBUserLoginUseCase: UseCaseContract<OBAuthInterface,OBUserProfile>,
    private val bioService : BiometricAuthService
) : ViewModel() {

    private val biometricCapabilitiesMutableState : MutableStateFlow<SupportedDeviceAuthenticationMethods>
            = MutableStateFlow(SupportedDeviceAuthenticationMethods.Waiting)
    val biometricCapabilitiesState : StateFlow<SupportedDeviceAuthenticationMethods> = biometricCapabilitiesMutableState

    private val autoLoginMutableState : MutableStateFlow<LoginState> = MutableStateFlow(
        LoginState.Loading
    )

    val autoLoginState : StateFlow<LoginState>  = autoLoginMutableState


    private val _navigationBarState : MutableStateFlow<MutableList<OBBottomNavigationBarItem>> = MutableStateFlow(
        mutableListOf(
            OBBottomNavigationBarItem(
                selectedStateIcon = R.drawable.ic_globe,
                unSelectedStateIcon = R.drawable.ic_globe
            ),
            OBBottomNavigationBarItem(
                selectedStateIcon = R.drawable.ic_marketplace,
                unSelectedStateIcon = R.drawable.ic_marketplace
            ),
            OBBottomNavigationBarItem(
                selectedStateIcon = R.drawable.ic_notifications,
                unSelectedStateIcon = R.drawable.ic_notifications_outlined,
                badgeContent = 0
            ),
            OBBottomNavigationBarItem(
                selectedStateIcon = R.drawable.ic_profile,
                unSelectedStateIcon = R.drawable.ic_profile_outlined
            )
        ))

      val navigationBarState : StateFlow<List<OBBottomNavigationBarItem>> = _navigationBarState


    init {
        checkBiometricCapabilitiesState()
        viewModelScope.launch {
            checkActiveSession()
        }
    }


    private suspend fun checkActiveSession() {
        oBUsersRepositoryInterface.getActiveUserSessionToken()?.let { token->
            runCatching {
                oBUserLoginUseCase.execute(OBAuthInterface.OBTokenLogin(token))
            }.onFailure { th->
                th.printStackTrace()
                autoLoginMutableState.update {
                    LoginState.Idle
                }
            }.onSuccess { data->
                autoLoginMutableState.update {
                    LoginState.Success(data)
                }
            }
        } ?: run {
            autoLoginMutableState.update {
                LoginState.Idle
            }
        }
    }


    fun checkBiometricCapabilitiesState(){
        biometricCapabilitiesMutableState.value = bioService.checkBiometricCapabilitiesState()
    }

      fun updateBadgeCount(itemIndex : Int,count : Int) {
          _navigationBarState.update { items->
              val badgeItem : OBBottomNavigationBarItem? = items.getOrNull(itemIndex)?.copy(
                  badgeContent = count
              )
              badgeItem?.run {
                  items[itemIndex] = badgeItem
              }
              items
          }
    }

}