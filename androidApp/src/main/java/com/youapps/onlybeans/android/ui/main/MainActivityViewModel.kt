package com.youapps.onlybeans.android.ui.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.youapps.designsystem.R
import com.youapps.designsystem.components.bars.SesameBottomNavigationBarItem
import com.youapps.onlybeans.data.repositories.users.OBUsersRepositoryInterface
import com.youapps.onlybeans.security.BiometricAuthService
import com.youapps.onlybeans.security.SupportedDeviceAuthenticationMethods
import com.youapps.users_management.ui.login.LoginState


class MainActivityViewModel(
    private val OBUsersRepositoryInterface: OBUsersRepositoryInterface,
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

}