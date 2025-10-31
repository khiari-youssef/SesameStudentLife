package com.youapps.onlybeans.android.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.youapps.designsystem.components.bars.OBBottomNavigationBarDefaults
import com.youapps.onlybeans.security.SupportedDeviceAuthenticationMethods

data class MainActivityStateHolder(
    val biometricSupportState : State<SupportedDeviceAuthenticationMethods>,
    val rootNavController : NavHostController,
    val homeDestinations : State<OBBottomNavigationBarDefaults>
) {


    companion object{
        @Composable
        fun rememberMainActivityState(
            biometricSupportState : State<SupportedDeviceAuthenticationMethods>,
            rootNavController : NavHostController,
            homeDestinations : State<OBBottomNavigationBarDefaults>
        )  : MainActivityStateHolder = remember(
            biometricSupportState,rootNavController,homeDestinations
        ){
            MainActivityStateHolder(
                biometricSupportState,rootNavController,homeDestinations
            )
        }
    }
}