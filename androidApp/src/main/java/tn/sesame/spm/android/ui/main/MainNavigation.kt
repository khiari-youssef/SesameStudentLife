package tn.sesame.spm.android.ui.main

import AppExitPopup
import SettingsScreen
import ViewUserProfilePopup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import androidx.navigation.navigation
import org.koin.androidx.compose.getViewModel
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.designsystem.components.modals.NavigationNotFoundModal
import tn.sesame.designsystem.navigateBack
import tn.sesame.spm.android.base.NavigationRoutingData
import tn.sesame.spm.android.base.NavigationRoutingData.ProjectJoinProcedure.ProjectDetailsScreen
import tn.sesame.spm.android.base.NavigationRoutingData.ProjectJoinProcedure.ProjectDocumentsDepositScreen
import tn.sesame.spm.android.ui.home.HomeScreen
import tn.sesame.users_management.ui.login.LoginScreen
import tn.sesame.users_management.ui.login.LoginState
import tn.sesame.users_management.ui.login.LoginUIStateHolder
import tn.sesame.users_management.ui.login.LoginViewModel
import tn.sesame.users_management.ui.settings.AppSettingsStateHolder
import tn.sesame.users_management.ui.settings.SettingsViewModel
import tn.sesame.spm.domain.entities.SesameUser

@Composable
fun MainActivity.MainNavigation(
    modifier: Modifier = Modifier,
    rootNavController : NavHostController,
    homeDestinations : SesameBottomNavigationBarDefaults,
    skipLogin : Boolean = false
) {
    NavHost(
        modifier = modifier,
        route = "MainGraph",
        startDestination = if (skipLogin) "MainNavigation" else NavigationRoutingData.Login,
        navController = rootNavController,
        builder = {
            composable(
                route = NavigationRoutingData.Login
            ){
                val viewModel : LoginViewModel = getViewModel()
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
                route = NavigationRoutingData.PrivacyPolicyScreen
            ){

            }
            composable(
                route = NavigationRoutingData.Settings
            ){
                val viewModel : SettingsViewModel = getViewModel()
                val uiState = AppSettingsStateHolder
                    .rememberAppSettingsState(
                        isAutoLoginEnabled =viewModel.getAutoLoginEnabled().collectAsStateWithLifecycle(
                            initialValue = false
                        )
                    )
                SettingsScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    uiState = uiState,
                    onItemSelectedStateChanged ={
                        viewModel.setAutoLoginEnabled(it)
                    },
                    onBackPressed = {
                        rootNavController.navigateBack()
                    }
                )
            }
        }
    )

}