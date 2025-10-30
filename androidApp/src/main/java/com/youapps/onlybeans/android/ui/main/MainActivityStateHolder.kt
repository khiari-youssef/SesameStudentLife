package com.youapps.onlybeans.android.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.youapps.designsystem.components.bars.SesameBottomNavigationBarDefaults
import com.youapps.onlybeans.security.SupportedDeviceAuthenticationMethods
import com.youapps.users_management.ui.login.LoginState

data class MainActivityStateHolder(
    val biometricSupportState : State<SupportedDeviceAuthenticationMethods>,
    val autoLoginState : State<LoginState>,
    val rootNavController : NavHostController,
    val homeDestinations : State<SesameBottomNavigationBarDefaults>
) {


    companion object{
        @Composable
        fun rememberMainActivityState(
            biometricSupportState : State<SupportedDeviceAuthenticationMethods>,
            autoLoginState : State<LoginState>,
            rootNavController : NavHostController,
            homeDestinations : State<SesameBottomNavigationBarDefaults>
        )  : MainActivityStateHolder = remember(
            biometricSupportState,autoLoginState,rootNavController,homeDestinations
        ){
            MainActivityStateHolder(
                biometricSupportState,autoLoginState,rootNavController,homeDestinations
            )
        }
    }
}