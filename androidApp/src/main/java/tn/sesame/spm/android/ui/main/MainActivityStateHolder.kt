package tn.sesame.spm.android.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.security.SupportedDeviceAuthenticationMethods

data class MainActivityStateHolder(
    val biometricSupportState : State<SupportedDeviceAuthenticationMethods>,
    val autoLoginState : State<LoginState>,
    val rootNavController : NavHostController,
    val homeDestinations : SesameBottomNavigationBarDefaults
) {
    companion object{
        @Composable
        fun rememberMainActivityState(
             biometricSupportState : State<SupportedDeviceAuthenticationMethods>,
             autoLoginState : State<LoginState>,
             rootNavController : NavHostController,
             homeDestinations : SesameBottomNavigationBarDefaults
        )  : MainActivityStateHolder = remember(
            biometricSupportState,autoLoginState,rootNavController,homeDestinations
        ){
            MainActivityStateHolder(
                biometricSupportState,autoLoginState,rootNavController,homeDestinations
            )
        }
    }
}