package tn.sesame.spm.android.ui.home


import ProfileScreen
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.getViewModel
import tn.sesame.designsystem.components.NavigationBarScreenTemplate
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBar
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.designsystem.components.menus.MenuOption
import tn.sesame.designsystem.components.menus.MenuOptions
import tn.sesame.spm.android.R
import tn.sesame.spm.android.base.NavigationRoutingData
import tn.sesame.spm.android.ui.notifications.NotificationScreenStateHolder
import tn.sesame.spm.android.ui.notifications.NotificationsScreen
import tn.sesame.spm.android.ui.notifications.NotificationsViewModel
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameTeacher
import tn.sesame.users_management.ui.profile.MyProfileViewModel
import tn.sesame.spm.security.BiometricLauncherService


@Composable
fun HomeScreen(
    homeDestinations: SesameBottomNavigationBarDefaults,
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
            .setEnterAnim(androidx.appcompat.R.anim.abc_fade_in)
            .setExitAnim(androidx.appcompat.R.anim.abc_fade_out)
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
                    route = NavigationRoutingData.Home.News
                ) {

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
                    val profileViewModel : MyProfileViewModel = getViewModel()
                    val displayBioAth = remember {
                        mutableStateOf(false)
                    }
                    if (displayBioAth.value){
                        RequireBiometricAuth(
                            onBiometricPassResult = { state->
                                if (state is BiometricLauncherService.DeviceAuthenticationState.Success){
                                    profileViewModel.logout()
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
                        val myProfile  = profileViewModel.getMyProfile().collectAsStateWithLifecycle(
                            initialValue = null
                        )
                         val menuOptions = MenuOptions(buildList {
                        when(myProfile.value) {
                            is SesameStudent -> {
                                add(
                                    MenuOption(
                                     id = "my_projects",
                                    iconRes = tn.sesame.designsystem.R.drawable.ic_project_outlined,
                                    label = stringResource(id = tn.sesame.users_management.R.string.profile_myprojects)
                                )
                                )
                                add(
                                    MenuOption(
                                        id = "my_subs",
                                    iconRes = tn.sesame.designsystem.R.drawable.ic_money_ops,
                                    label = stringResource(id = tn.sesame.users_management.R.string.profile_my_subs)
                                )
                                )
                                add(
                                    MenuOption(
                                        id = "my_grades",
                                    iconRes = tn.sesame.designsystem.R.drawable.ic_report,
                                    label = stringResource(id = tn.sesame.users_management.R.string.profile_my_grades)
                                )
                                )
                            }
                            is SesameTeacher -> {
                                add(
                                    MenuOption(
                                    id = "my_classes",
                                    iconRes = tn.sesame.designsystem.R.drawable.ic_project_outlined,
                                    label = stringResource(id = tn.sesame.users_management.R.string.profile_myclasses)
                                )
                                )
                            }
                        }

                        addAll(listOf(
                            MenuOption(
                                id = "privacy_policy",
                                iconRes = tn.sesame.designsystem.R.drawable.ic_policy ,
                                label = stringResource(id = tn.sesame.users_management.R.string.profile_policy)
                            ),
                            MenuOption(
                                id = "settings",
                                iconRes = tn.sesame.designsystem.R.drawable.ic_settings ,
                                label = stringResource(id = tn.sesame.users_management.R.string.profile_settings)
                            )
                        ))
                    })
                        myProfile.value?.run {
                            ProfileScreen(
                                modifier = modifier
                                    .fillMaxSize(),
                                sesameUser = this ,
                                menuOptions = menuOptions,
                                onMenuItemClicked = {optionIndex->
                                    when (menuOptions.options[optionIndex].id){
                                        "my_projects"-> {
                                            onHomeExit("${NavigationRoutingData.MyProjects}/1a2dhsd5h5fhsf2s2")
                                        }
                                        "privacy_policy"-> {
                                            onHomeExit(NavigationRoutingData.PrivacyPolicyScreen)
                                        }
                                        "settings"-> {
                                            onHomeExit(NavigationRoutingData.Settings)
                                        }
                                        "my_classes" ->{

                                        }
                                        "my_grades" -> {

                                        }
                                        "my_subs" -> {

                                        }
                                        else -> {

                                        }
                                    }
                                },
                                onLogOutClicked = {
                                    displayBioAth.value = true
                                }
                            )
                        }

                    }
                }
            }
        }
    )
}