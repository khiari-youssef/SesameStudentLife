package tn.sesame.spm.android.ui.home

import ProfileScreen
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tn.sesame.designsystem.components.NavigationBarScreenTemplate
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBar
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.base.NavigationRoutingData


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
                                    modifier = Modifier
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
                                text = "Projects Template",
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
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
                        val localContext = LocalContext.current
                        ProfileScreen(
                            modifier = modifier
                                .fillMaxSize(),
                            onMenuItemClicked = {

                            },
                            onLogOutClicked = {

                            },
                            onProfileEmailClicked = { email ->
                                val intent = Intent(Intent.ACTION_VIEW)
                                val data = Uri.parse(
                                    "mailto:$email?subject=" + Uri.encode("") + "&body=" + Uri.encode(
                                        ""
                                    )
                                )
                                intent.setData(data)
                                startActivity(
                                    localContext, Intent.createChooser(
                                        intent, "Choose an app"
                                    ), Bundle()
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}