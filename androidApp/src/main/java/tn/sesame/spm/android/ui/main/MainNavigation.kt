package tn.sesame.spm.android.ui.main

import AppExitPopup
import MyProjectsScreen
import ProjectDetailsScreen
import ProjectDocumentsDepositScreen
import ProjectJoinProcedureResultScreen
import ProjectSupervisorSelectionScreen
import ProjectTeammatesSelectionScreen
import ProjectTechnologiesSelectionScreen
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
import tn.sesame.spm.android.base.NavigationRoutingData
import tn.sesame.spm.android.base.NavigationRoutingData.ProjectJoinProcedure.ProjectDetailsScreen
import tn.sesame.spm.android.base.NavigationRoutingData.ProjectJoinProcedure.ProjectDocumentsDepositScreen
import tn.sesame.spm.android.ui.home.HomeScreen
import tn.sesame.users_management.ui.login.LoginScreen
import tn.sesame.users_management.ui.login.LoginState
import tn.sesame.users_management.ui.login.LoginUIStateHolder
import tn.sesame.users_management.ui.login.LoginViewModel
import tn.sesame.projects_management.ui.projects.ProjectsViewModel
import tn.sesame.projects_management.ui.projects.SesameProjectsState
import tn.sesame.projects_management.ui.projects.SesameProjectsStateHolder
import tn.sesame.projects_management.ui.projects.joinProcedure.SesameProjectActorsListState
import tn.sesame.projects_management.ui.projects.joinProcedure.SesameProjectJoinRequestCollaboratorsSelectionStateHolder
import tn.sesame.projects_management.ui.projects.joinProcedure.SesameProjectJoinRequestSupervisorSelectionStateHolder
import tn.sesame.spm.android.ui.settings.AppSettingsStateHolder
import tn.sesame.spm.android.ui.settings.SettingsViewModel
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
                        rootNavController.popBackStack()
                    }
                )
            }
            navigation(
                route = "${NavigationRoutingData.ProjectJoinProcedure}",
                startDestination = NavigationRoutingData.ProjectJoinProcedure.ProjectDetailsScreen
            ){
                val goBackToPreviousScreenAction : ()->Unit = {
                    rootNavController.popBackStack()
                }
                NavigationRoutingData.ProjectJoinProcedure.run {
                    composable(
                        route = "$ProjectDetailsScreen/{projectID}",
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
                                    onBackPressed = goBackToPreviousScreenAction,
                                    onJoinButtonClicked = {
                                        rootNavController.navigate(route = SupervisorSelectionScreen)
                                    }
                                )
                            } ?: NavigationNotFoundModal()
                        } ?: NavigationNotFoundModal()
                    }
                    composable(SupervisorSelectionScreen){
                        val viewModel : ProjectsViewModel = getViewModel()
                        val uiState = SesameProjectJoinRequestSupervisorSelectionStateHolder
                            .rememberSesameProjectJoinRequestFormState(
                                availableSuperVisors = viewModel.getAvailableCollaborators().collectAsStateWithLifecycle(
                                    initialValue = SesameProjectActorsListState.Loading
                                ),
                                selectedSupervisorIndex = remember {
                                    mutableStateOf(null)
                                }
                            )
                         ProjectSupervisorSelectionScreen(
                             modifier = Modifier
                                 .systemBarsPadding()
                                 .fillMaxSize(),
                             uiState = uiState,
                             onBackPressed = goBackToPreviousScreenAction,
                             onItemSelectedIndexStateChanged = {index ->
                                 uiState.selectedSupervisorIndex.value = index
                             },
                             onNextStepButtonClicked = {
                                 rootNavController.navigate(
                                     route = TeammatesSelectionScreen
                                 )
                             }
                         )
                    }
                    composable(TeammatesSelectionScreen){
                        val viewModel : ProjectsViewModel = getViewModel()
                        val uiState = SesameProjectJoinRequestCollaboratorsSelectionStateHolder
                            .rememberSesameProjectJoinRequestFormState(
                                availableSuperVisors = viewModel.getAvailableCollaborators().collectAsStateWithLifecycle(
                                    initialValue = SesameProjectActorsListState.Loading
                                )
                            )
                        ProjectTeammatesSelectionScreen(
                            modifier = Modifier
                                .systemBarsPadding()
                                .fillMaxSize(),
                            onBackPressed = goBackToPreviousScreenAction,
                            uiState = uiState ,
                            onNextStepButtonClicked = {
                                rootNavController.navigate(
                                    route = TechnologiesSelectionScreen
                                )
                            },
                            onItemSelectedStateChanged = {index,isSelected ->
                                if (isSelected){
                                    uiState.collaboratorsSelectionStateArray.add(index)
                                } else {
                                    uiState.collaboratorsSelectionStateArray.remove(index)
                                }
                            }
                        )
                    }
                    composable(TechnologiesSelectionScreen){
                        ProjectTechnologiesSelectionScreen(
                            modifier = Modifier
                                .systemBarsPadding()
                                .fillMaxSize(),
                            onBackPressed = goBackToPreviousScreenAction
                        )
                    }
                    composable(ProjectDocumentsDepositScreen){
                        ProjectDocumentsDepositScreen(
                            modifier = Modifier
                                .systemBarsPadding()
                                .fillMaxSize(),
                            onBackPressed = goBackToPreviousScreenAction
                        )
                    }
                    composable(JoinRequestResultScreen){
                        ProjectJoinProcedureResultScreen(
                            modifier = Modifier
                                .systemBarsPadding()
                                .fillMaxSize(),
                            onBackPressed = {

                            }
                        )
                    }
                }

            }
        }
    )

}