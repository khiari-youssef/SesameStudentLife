package com.youapps.onlybeans.android.ui.home


import ProfileScreen
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.youapps.designsystem.components.NavigationBarScreenTemplate
import com.youapps.designsystem.components.bars.OBBottomNavigationBar
import com.youapps.designsystem.components.bars.OBBottomNavigationBarDefaults
import com.youapps.designsystem.components.popups.LogoutPopup
import com.youapps.onlybeans.android.base.NavigationRoutingData
import com.youapps.onlybeans.android.ui.notifications.NotificationScreenStateHolder
import com.youapps.onlybeans.android.ui.notifications.NotificationsScreen
import com.youapps.onlybeans.android.ui.notifications.NotificationsViewModel
import com.youapps.users_management.ui.profile.MyProfileViewModel
import com.youapps.users_management.ui.profile.ProfileScreenState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    homeDestinations: OBBottomNavigationBarDefaults,
    onHomeExit: (route: String) -> Unit
) {
    val homeNavController = rememberNavController()

    val selectedHomeDestinationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    val initialRoute = remember {
       derivedStateOf {
           NavigationRoutingData.Home.mapIndexToRoute(selectedHomeDestinationIndex.intValue)
       }
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
            .semantics {
                contentDescription = "HomeScreen"
            }
            .fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomAppBarVisible.value,
                enter = fadeIn(spring()),
                exit = fadeOut(spring())
            ) {
                OBBottomNavigationBar(
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
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                navController = homeNavController,
                route = NavigationRoutingData.Home.ROOT,
                startDestination = initialRoute.value
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

                            }
                        }
                    )
                }
                composable(
                    route = NavigationRoutingData.Home.News
                ) {

                }
                composable(NavigationRoutingData.Home.Notifications) {
                    val viewModel = koinViewModel<NotificationsViewModel>()
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
                                    onHomeExit("${NavigationRoutingData.ProjectJoinProcedure.ProjectDetailsScreen}/$projectRef")
                                }
                            },
                            onRefreshNotifications = {
                                viewModel.getLastNotifications(isRefresh = true)
                            }
                        )
                    }
                }
                composable(NavigationRoutingData.Home.Profile) {
                    val profileViewModel : MyProfileViewModel = koinViewModel()
                    var isLogoutPopupVisible by remember {
                        mutableStateOf(false)
                    }
                    val profileScreenCoScope = rememberCoroutineScope()
                    val currentContext = LocalContext.current

                    LogoutPopup(
                        isShown = isLogoutPopupVisible,
                        onCancelled =  {
                            isLogoutPopupVisible = false
                        },
                        onConfirmAppExit = {
                            profileScreenCoScope.launch {
                                profileViewModel.logOutCurrentUser().collect { isLoggedOut->
                                    if (isLoggedOut) {
                                    onHomeExit(NavigationRoutingData.Login)
                                    } else {
                                        Toast.makeText(currentContext, "Could not logout user !", Toast.LENGTH_LONG).show()
                                    }
                                    isLogoutPopupVisible = false
                                }
                            }
                        }
                    )

                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .systemBarsPadding()
                            .padding(paddingValues),
                        onExitNavigation = { onHomeExit(NavigationRoutingData.ExitAppRoute) },
                    ) { modifier ->
                        val myProfileState : State<ProfileScreenState> = profileViewModel.profileState.collectAsStateWithLifecycle(
                            initialValue = ProfileScreenState.Loading()
                        )

                            val scrollState = rememberScrollState()

                            ProfileScreen(
                                modifier = modifier
                                    .verticalScroll(state = scrollState)
                                    .fillMaxSize(),
                                screenState = myProfileState.value,
                                onRefreshProfile = {
                                    profileViewModel.getMyProfile(withRefresh = true)
                                },
                                onLogOutClicked = {
                                    isLogoutPopupVisible = true
                                }

                            )
                        }
                }
            }
        }
    )
}