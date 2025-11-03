package com.youapps.onlybeans.android.ui.main

import SettingsScreen
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.youapps.designsystem.components.bars.OBBottomNavigationBarDefaults
import com.youapps.designsystem.components.dialogs.NavigationNotFoundModal
import com.youapps.designsystem.components.popups.AppExitPopup
import com.youapps.designsystem.navigateBack
import com.youapps.onlybeans.android.base.NavigationRoutingData
import com.youapps.onlybeans.android.ui.home.HomeScreen
import com.youapps.users_management.ui.login.LoginScreen
import com.youapps.users_management.ui.login.LoginState
import com.youapps.users_management.ui.login.LoginUIStateHolder
import com.youapps.users_management.ui.login.LoginViewModel
import com.youapps.users_management.ui.settings.AppSettingsStateHolder
import com.youapps.users_management.ui.settings.SettingsViewModel
import com.youapps.users_management.ui.settings.mygrades.MyGradesScreen
import com.youapps.users_management.ui.settings.mysubscriptions.MySubscriptionsScreen
import com.youapps.users_management.ui.settings.privacypolicy.PrivacyPolicyScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainActivity.MainNavigation(
    modifier: Modifier = Modifier,
    rootNavController : NavHostController,
    homeDestinations : State<OBBottomNavigationBarDefaults>,
    skipLogin : Boolean = false
) {
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
    NavHost(
        modifier = modifier,
        route = "MainGraph",
        startDestination = if (skipLogin) "MainNavigation" else NavigationRoutingData.Login,
        navController = rootNavController,
        builder = {
            composable(
                route = NavigationRoutingData.Login
            ){
                val viewModel : LoginViewModel = koinViewModel()
                val loginUIState = LoginUIStateHolder.rememberLoginUIState(
                    loginEmail = rememberSaveable {
                        mutableStateOf("")
                    },
                    loginPassword = rememberSaveable {
                        mutableStateOf("")
                    },
                    loginRequestResult = viewModel.loginResultState.collectAsStateWithLifecycle()
                )
                LaunchedEffect(key1 = loginUIState.loginRequestResult.value, block = {
                    if (loginUIState.loginRequestResult.value is LoginState.Success){
                        rootNavController.navigate("MainNavigation")
                    }
                } )

                BackHandler {
                    isAppExistPopupShown.value = true
                }
                LoginScreen(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "LoginScreen"
                        }
                        .fillMaxSize(),
                    loginUIStateHolder = loginUIState,
                    onEmailChanged = { email ->
                        loginUIState.loginEmail.value = email
                    },
                    onPasswordChanged ={ password ->
                        loginUIState.loginPassword.value = password
                    },
                    onSetIdleState = {
                        viewModel.setLoginIdleState()
                    }
                ){
                    viewModel.loginWithEmailAndPassword(
                        loginUIState.loginEmail.value,
                        loginUIState.loginPassword.value
                    )
                }
            }
            composable(
                route = "MainNavigation"
            ){ _->

                HomeScreen(
                    homeDestinations = homeDestinations.value,
                    onHomeExit = {destination->
                        when (destination){
                            NavigationRoutingData.ExitAppRoute->{
                                isAppExistPopupShown.value = true
                            }
                            NavigationRoutingData.Login->{
                                rootNavController.navigate(
                                    destination,
                                    NavOptions.Builder()
                                        .setPopUpTo(NavigationRoutingData.Login,true)
                                        .build()
                                )
                            }
                            else ->{
                                rootNavController.navigate(destination)
                            }
                        }
                    }
                )
                BackHandler {
                    isAppExistPopupShown.value = true
                }
            }
            composable(
                route = "${NavigationRoutingData.MyProjects}/{userID}",
                arguments = listOf(navArgument("userID") {
                    type = NavType.StringType
                })
            ) { backStackEntry->

            }
            composable(
                route = NavigationRoutingData.NavigationNotFound
            ){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    NavigationNotFoundModal(
                        modifier = Modifier
                    )
                }
            }
            composable(
                route = NavigationRoutingData.MyClasses
            ){

            }
            composable(
                route = NavigationRoutingData.MySubscriptions
            ){
                MySubscriptionsScreen(
                    onBackPressed = {
                        rootNavController.navigateBack()
                    }
                )
            }
            composable(
                route = NavigationRoutingData.MyGrades
            ){
                MyGradesScreen(
                    onBackPressed = {
                        rootNavController.navigateBack()
                    }
                )
            }
            composable(
                route = NavigationRoutingData.PrivacyPolicyScreen
            ){
                PrivacyPolicyScreen(
                    onBackPressed = {
                        rootNavController.navigateBack()
                    }
                )
            }
            composable(
                route = NavigationRoutingData.Settings
            ){
                val viewModel : SettingsViewModel = koinViewModel()
                val uiState = AppSettingsStateHolder
                    .rememberAppSettingsState(
                        isAutoLoginEnabled = remember {  mutableStateOf(false) }
                    )
                SettingsScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    uiState = uiState,
                    onItemSelectedStateChanged ={

                    },
                    onBackPressed = {
                        rootNavController.navigateBack()
                    }
                )
            }

        }
    )

}