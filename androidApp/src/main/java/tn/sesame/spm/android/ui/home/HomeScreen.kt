package tn.sesame.spm.android.ui.home


import ProfileScreen
import ProjectsScreen
import RequireBiometricAuth
import SesameDateRangePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.withCreated
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.compose.getViewModel
import tn.sesame.designsystem.components.NavigationBarScreenTemplate
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBar
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.base.NavigationRoutingData
import tn.sesame.spm.android.ui.notifications.NotificationScreenStateHolder
import tn.sesame.spm.android.ui.notifications.NotificationsScreen
import tn.sesame.spm.android.ui.notifications.NotificationsViewModel
import tn.sesame.spm.android.ui.projects.ProjectsViewModel
import tn.sesame.spm.android.ui.projects.SesameProjectsState
import tn.sesame.spm.android.ui.projects.SesameProjectsStateHolder
import tn.sesame.spm.domain.entities.SesameClass
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameUserSex
import tn.sesame.spm.security.BiometricLauncherService


@Composable
fun HomeScreen(
    homeDestinations: SesameBottomNavigationBarDefaults,
    onHomeExit: (route: String) -> Unit
) {
    val homeNavController = rememberNavController()
    val initialRoute = remember {
        NavigationRoutingData.Home.Calendar
    }
    val selectedHomeDestinationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }
    val navOpts = remember {
        NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()
    }
    val isBottomAppBarVisible = rememberSaveable {
        mutableStateOf(true)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomAppBarVisible.value,
                enter = fadeIn(spring()),
                exit = fadeOut(spring())
            ) {
                SesameBottomNavigationBar(
                    modifier = Modifier
                        .heightIn(min = 24.dp, max = 56.dp)
                        .fillMaxWidth(),
                    selectedItemIndex = selectedHomeDestinationIndex.intValue,
                    properties = homeDestinations,
                    onItemSelected = { index ->
                        selectedHomeDestinationIndex.intValue = index
                        homeNavController.navigate(
                            route = NavigationRoutingData.Home.mapIndexToRoute(index),
                            navOptions = navOpts
                        )
                    }
                )
            }
        },
        content = { paddingValues ->
            NavHost(
                navController = homeNavController,
                route = NavigationRoutingData.Home.ROOT,
                startDestination = initialRoute
            ) {
                composable(NavigationRoutingData.Home.Calendar) {
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = remember {
                            {
                                onHomeExit(NavigationRoutingData.ExitAppRoute)
                            }
                        },
                        content = remember {
                            { modifier ->
                                SesameDateRangePicker(
                                    modifier = modifier
                                        .fillMaxSize()
                                        .clickable {
                                            isBottomAppBarVisible.value =
                                                isBottomAppBarVisible.value.not()
                                        }
                                )
                            }
                        }
                    )
                }
                composable(
                    route = NavigationRoutingData.Home.Projects
                ) {
                    val viewModel : ProjectsViewModel = getViewModel()

                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = { onHomeExit(NavigationRoutingData.ExitAppRoute) },
                    ) { modifier ->
                        val uiState = SesameProjectsStateHolder.rememberSesameProjectsState(
                            currentSearchQuery = rememberSaveable {
                                mutableStateOf("")
                            },
                            currentProjects = viewModel.currentProjectsState.collectAsStateWithLifecycle(
                                initialValue = SesameProjectsState.Loading
                            )
                        )

                        LaunchedEffect(key1 = uiState.currentSearchQuery.value, block = {
                                viewModel.refreshProjects(keywordsFilter = uiState.currentSearchQuery.value)
                        } )
                        ProjectsScreen(
                            modifier = modifier,
                            uiState = uiState,
                            onSearchQueryChanged = { newQuery->
                                if (newQuery.trim().lowercase() != uiState.currentSearchQuery.value.trim().lowercase()) {
                                    uiState.currentSearchQuery.value = newQuery
                                }
                            },
                            onViewDetails = {projectID->
                             onHomeExit("${NavigationRoutingData.ProjectDetails}/$projectID")
                            },
                            onJoinRequest = {

                            }
                        )
                    }
                }
                composable(NavigationRoutingData.Home.Notifications) {
                    val viewModel = getViewModel<NotificationsViewModel>()
                    val screenState =  NotificationScreenStateHolder
                        .rememberNotificationScreenState(
                            notificationsListState = viewModel.latestNotificationsState.collectAsStateWithLifecycle(),
                            currentPage = rememberSaveable {
                                mutableIntStateOf(0)
                            }
                        )
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = { onHomeExit(NavigationRoutingData.ExitAppRoute) },
                    ) { modifier ->
                        NotificationsScreen(
                            modifier = modifier,
                            screenState =  screenState,
                            onProjectReferenceClicked = {projectRef->
                                if (projectRef.isNotBlank()){
                                    onHomeExit("${NavigationRoutingData.ProjectDetails}/$projectRef")
                                }
                            },
                            onRefreshNotifications = {
                                viewModel.getLastNotifications(isRefresh = true)
                            }
                        )
                    }
                }
                composable(NavigationRoutingData.Home.Profile) {
                    val displayBioAth = remember {
                        mutableStateOf(false)
                    }
                    if (displayBioAth.value){
                        RequireBiometricAuth(
                            onBiometricPassResult = { state->
                                if (state is BiometricLauncherService.DeviceAuthenticationState.Success){
                                    onHomeExit(NavigationRoutingData.Login)
                                }
                            }
                        )
                    }
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .systemBarsPadding()
                            .padding(paddingValues),
                        onExitNavigation = { onHomeExit(NavigationRoutingData.ExitAppRoute) },
                    ) { modifier ->
                        ProfileScreen(
                            sesameUser = SesameStudent(
                                "aehf",
                                "Khiari",
                                "Youssef",
                                "khiari.youssef98@gmail.com",
                                SesameUserSex.Male,
                                "",
                                "",
                                "Android Engineer",
                                SesameClass(
                                    "ingta4-c",
                                    "INGTA",
                                    "4",
                                    "C"
                                )
                            ),
                            modifier = modifier
                                .fillMaxSize(),
                            onMenuItemClicked = {
                              onHomeExit("${NavigationRoutingData.MyProjects}/1a2dhsd5h5fhsf2s2")
                            },
                            onLogOutClicked = {
                                displayBioAth.value = true
                            }
                        )
                    }
                }
            }
        }
    )
}