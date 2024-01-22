import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.designsystem.components.modals.NavigationNotFoundModal
import tn.sesame.spm.android.base.NavigationRoutingData
import tn.sesame.spm.android.ui.home.HomeScreen
import tn.sesame.spm.android.ui.login.LoginScreen
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.android.ui.login.LoginUIStateHolder
import tn.sesame.spm.android.ui.projects.ProjectsViewModel
import tn.sesame.spm.android.ui.projects.SesameProjectsState
import tn.sesame.spm.android.ui.projects.SesameProjectsStateHolder
import tn.sesame.spm.domain.entities.SesameProjectMember
import tn.sesame.spm.domain.entities.SesameUser

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
                route = "${NavigationRoutingData.ProjectDetails}/{projectID}",
                arguments = listOf(navArgument("projectID") {
                    type = NavType.StringType
                })
            ){args->
                val isUserProfilePopupShown : MutableState<SesameUser?> = remember {
                    mutableStateOf(null)
                }
                ViewUserProfilePopup(
                    sesameUser = isUserProfilePopupShown.value,
                    isShown =isUserProfilePopupShown.value != null
                ) {
                    isUserProfilePopupShown.value = null
                }
                val projectId = args.arguments?.getString("projectID")
                projectId?.takeIf { it.isNotBlank() }?.run {
                    val viewModel : ProjectsViewModel = getViewModel()
                    val project = viewModel.getProjectById(projectId).collectAsStateWithLifecycle(
                        initialValue = null
                    )
                    project.value?.run {
                        ProjectDetailsScreen(
                            modifier = Modifier
                                .systemBarsPadding()
                                .fillMaxSize(),
                            project = this,
                            onShowMember = { member->
                                isUserProfilePopupShown.value = member
                            },
                            onBackPressed = {
                                rootNavController.popBackStack()
                            }
                        )
                    } ?: NavigationNotFoundModal()
                } ?: NavigationNotFoundModal()
            }
            composable(
                route = "${NavigationRoutingData.MyProjects}/{userID}",
                arguments = listOf(navArgument("userID") {
                    type = NavType.StringType
                })
            ) { backStackEntry->
                val viewModel : ProjectsViewModel = getViewModel()
                val uiState = SesameProjectsStateHolder.rememberSesameProjectsState(
                    currentSearchQuery = rememberSaveable {
                        mutableStateOf("")
                    },
                    currentProjects = viewModel.currentProjectsState.collectAsStateWithLifecycle(
                        initialValue = SesameProjectsState.Loading
                    )
                )
                val userID = backStackEntry.arguments?.getString("userID")
                LaunchedEffect(key1 = uiState.currentSearchQuery.value, block = {
                    viewModel.refreshProjects(userID = userID,keywordsFilter = uiState.currentSearchQuery.value)
                } )
                MyProjectsScreen(
                   modifier = Modifier
                       .fillMaxSize(),
                    uiState = uiState ,
                    onSearchQueryChanged = { newQuery->

                    },
                    onViewDetails = { projectID->

                    },
                    onBackPressed = {
                        rootNavController.popBackStack()
                    }
                )
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

            }
        }
    )

}