package tn.sesame.spm.android.ui.home

import ProfileScreen
import ProjectList
import ProjectsScreen
import SesameDateRangePicker
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDateTime
import okhttp3.internal.cacheGet
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.get
import org.koin.compose.koinInject
import tn.sesame.designsystem.components.NavigationBarScreenTemplate
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBar
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.R
import tn.sesame.spm.android.base.NavigationRoutingData
import tn.sesame.spm.android.ui.projects.SesameProjectsStateHolder
import tn.sesame.spm.domain.entities.SesameClass
import tn.sesame.spm.domain.entities.SesameProject
import tn.sesame.spm.domain.entities.SesameProjectCollaborator
import tn.sesame.spm.domain.entities.SesameProjectJoinRequestState
import tn.sesame.spm.domain.entities.SesameStudent
import tn.sesame.spm.domain.entities.SesameSupervisor
import tn.sesame.spm.security.BiometricAuthService
import tn.sesame.spm.security.BiometricLauncherService
import tn.sesame.spm.security.SupportedDeviceAuthenticationMethods


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
                composable(NavigationRoutingData.Home.Projects) {
                    val allProjects = ProjectList(List(12){
                        SesameProject(
                            id ="fakeid-$it",
                            type = "StudentLifeWebsite",
                            description = "Design and build48$it a website for sesame students to ease the access to the services of sesame as well the daily routine of students and staff",
                            supervisor = SesameSupervisor(
                                "blabla-id",
                                "mblala@gmail.com",
                                "Monsieur blabla",
                                ""
                            ),
                            collaboratorsToJoin = if (it == 6)
                                listOf(
                                    SesameProjectCollaborator(
                                        "collabid",
                                        "ahmed@sesame.com.tn",
                                        "Ahmed",
                                        "",
                                        SesameProjectJoinRequestState.ACCEPTED
                                    ),
                                    SesameProjectCollaborator(
                                        "youssef-id",
                                        "youssef.khiari@sesame.com.tn",
                                        "Youssef",
                                        "",
                                        SesameProjectJoinRequestState.WAITING_APPROVAL
                                    )
                                )
                            else listOf(
                                SesameProjectCollaborator(
                                    "collabid",
                                    "ahmed@sesame.com.tn",
                                    "Ahmed",
                                    "",
                                    SesameProjectJoinRequestState.ACCEPTED
                                )
                            ),
                            maxCollaborators = 5,
                            duration = "2024-03-01T08:30:00".toLocalDateTime().."2024-08-15T08:30:00".toLocalDateTime(),
                            creationDate = "2023-11-01T20:30:00".toLocalDateTime(),
                            presentationDate = "2024-07-10T08:30:00".toLocalDateTime(),
                            keywords = listOf("WebDev","UI","Backend"),
                            techStack = listOf(
                                "Angular",
                                "NodeJS",
                                "MySql"
                            )
                        )
                    })
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = { onHomeExit(NavigationRoutingData.ExitAppRoute) },
                    ) { modifier ->
                        val uiState = SesameProjectsStateHolder.rememberSesameProjectsState(
                            currentSearchQuery = rememberSaveable {
                                mutableStateOf("")
                            },
                            currentProjects = remember {
                                mutableStateOf(allProjects)
                            }
                        )
                        ProjectsScreen(
                            modifier = modifier,
                            uiState = uiState,
                            onSearchQueryChanged = { newQuery->
                                 uiState.currentSearchQuery.value = newQuery
                                uiState.currentProjects.value = ProjectList(
                                    allProjects.projects.filter {
                                        it.description.contains(newQuery)
                                    }
                                )
                            },
                            onViewDetails = {

                            },
                            onJoinRequest = {

                            }
                        )
                    }
                }
                composable(NavigationRoutingData.Home.Notifications) {
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = { onHomeExit(NavigationRoutingData.ExitAppRoute) },
                    ) { modifier ->
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Notifications",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
                composable(NavigationRoutingData.Home.Profile) {
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .systemBarsPadding()
                            .padding(paddingValues),
                        onExitNavigation = { onHomeExit(NavigationRoutingData.ExitAppRoute) },
                    ) { modifier ->
                         val bioService : BiometricAuthService = koinInject()
                        val localContext = LocalContext.current as FragmentActivity
                        val authScope = rememberCoroutineScope()
                        val bioAuthTitle = stringResource(id = R.string.biometric_auth_dialog_title)
                        val bioAuthSubtitle = stringResource(id = R.string.biometric_auth_dialog_message)
                        ProfileScreen(
                            sesameUser = SesameStudent(
                                "aehf",
                                "Khiari",
                                "Youssef",
                                "khiari.youssef98@gmail.com",
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

                            },
                            onLogOutClicked = {
                                val state = bioService.checkBiometricCapabilitiesState()
                                if (state is SupportedDeviceAuthenticationMethods.Available){
                                    authScope.launch {
                                        state.biometricLauncherService.launch(
                                            activity = localContext,
                                            title = bioAuthTitle,
                                            subtitle = bioAuthSubtitle
                                        ).collectLatest {state->
                                              if (state is BiometricLauncherService.DeviceAuthenticationState.Success){
                                                  onHomeExit(NavigationRoutingData.Login)
                                              }
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}