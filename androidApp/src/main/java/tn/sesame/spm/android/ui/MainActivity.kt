package tn.sesame.spm.android.ui

import AppExitPopup
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.android.ext.android.inject
import tn.sesame.designsystem.SesameTheme
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.ui.home.HomeScreen
import tn.sesame.spm.android.base.NavigationRoutingData
import tn.sesame.spm.android.ui.login.LoginForm
import tn.sesame.spm.android.ui.login.LoginScreen
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.android.ui.login.LoginUIStateHolder
import tn.sesame.spm.security.BiometricAuthService

class MainActivity : ComponentActivity() {

    val bioService : BiometricAuthService by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isBiometricSupportAvailable = bioService.checkBiometricCapabilitiesState()
            .isAvailable()
        installSplashScreen().setKeepOnScreenCondition{
            isBiometricSupportAvailable.not()
        }
        setContent {
            val rootNavController = rememberNavController()
            val homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
            SesameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        modifier = Modifier,
                        route = "MainGraph",
                        startDestination = NavigationRoutingData.Login,
                        navController = rootNavController,
                        builder = {
                            composable(
                                route = NavigationRoutingData.Login
                            ){_->
                                val loginUIState = LoginUIStateHolder.rememberLoginUIState(
                                    loginEmail = rememberSaveable {
                                        mutableStateOf("")
                                    },
                                    loginPassword = rememberSaveable {
                                        mutableStateOf("")
                                    },
                                    loginRequestResult = remember {
                                        mutableStateOf(LoginState.Idle)
                                    }
                                )
                                LoginScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    loginUIStateHolder = loginUIState,
                                    onEmailChanged = { email ->
                                       loginUIState.loginEmail.value = email
                                    },
                                    onPasswordChanged ={ password ->
                                       loginUIState.loginPassword.value = password
                                    }
                                ){
                                    loginUIState.loginRequestResult.value = LoginState.Loading
                                }
                            }
                            composable(
                                route = "Home"
                            ){ _->
                                val isAppExistPopupShown = remember {
                                    mutableStateOf(false)
                                }
                                AppExitPopup(
                                    isShown =isAppExistPopupShown.value,
                                    onConfirmAppExit = {
                                        this@MainActivity.finishAffinity()
                                    },
                                    onCancelled = {
                                        isAppExistPopupShown.value = false
                                    }
                                )
                                HomeScreen(
                                    homeDestinations = homeDestinations,
                                   onHomeExit = {destination->
                                       if (destination == NavigationRoutingData.ExitAppRoute){
                                           isAppExistPopupShown.value = true
                                       } else {
                                           rootNavController.navigate(destination)
                                       }
                                   }
                                )
                                BackHandler {
                                    isAppExistPopupShown.value = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}


