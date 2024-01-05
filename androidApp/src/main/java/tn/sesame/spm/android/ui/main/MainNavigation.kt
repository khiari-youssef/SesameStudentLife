import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.base.NavigationRoutingData
import tn.sesame.spm.android.ui.home.HomeScreen
import tn.sesame.spm.android.ui.login.LoginScreen
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.android.ui.login.LoginUIStateHolder

@Composable
fun Activity.MainNavigation(
    modifier: Modifier = Modifier,
    rootNavController : NavHostController,
    homeDestinations : SesameBottomNavigationBarDefaults
) {
    NavHost(
        modifier = modifier,
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
                val fakeScope = rememberCoroutineScope()
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
                    fakeScope.launch {
                        delay(1000)
                        rootNavController.navigate("MainNavigation")
                    }

                }
            }
            composable(
                route = "MainNavigation"
            ){ _->
                val isAppExistPopupShown = remember {
                    mutableStateOf(false)
                }
                AppExitPopup(
                    isShown =isAppExistPopupShown.value,
                    onConfirmAppExit = {
                        this@MainNavigation.finishAffinity()
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
                        }else if (destination == NavigationRoutingData.Login){
                            rootNavController.navigate(
                                destination,
                                NavOptions.Builder()
                                    .setPopUpTo(NavigationRoutingData.Login,true)
                                    .build()
                            )
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