package tn.sesame.spm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import tn.sesame.designsystem.SesameTheme
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val rootNavController = rememberNavController()
            val homeDestinations = SesameBottomNavigationBarDefaults(
                    items = listOf(
                        SesameBottomNavigationBarItem(
                            selectedStateIcon = tn.sesame.designsystem.R.drawable.ic_calendar,
                            unSelectedStateIcon = tn.sesame.designsystem.R.drawable.ic_calendar_outlined
                        ),
                        SesameBottomNavigationBarItem(
                            selectedStateIcon = tn.sesame.designsystem.R.drawable.ic_project,
                            unSelectedStateIcon = tn.sesame.designsystem.R.drawable.ic_project_outlined
                        ),
                        SesameBottomNavigationBarItem(
                            selectedStateIcon = tn.sesame.designsystem.R.drawable.ic_notifications,
                            unSelectedStateIcon = tn.sesame.designsystem.R.drawable.ic_notifications_outlined
                        ),
                        SesameBottomNavigationBarItem(
                            selectedStateIcon = tn.sesame.designsystem.R.drawable.ic_profile,
                            unSelectedStateIcon = tn.sesame.designsystem.R.drawable.ic_profile_outlined
                        )
                    )
                )

            SesameTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        modifier = Modifier,
                        route = "MainGraph",
                        startDestination = "Home",
                        navController = rootNavController,
                        builder = {
                            composable(
                                route = NavigationRoutingData.Login
                            ){_->

                            }
                            composable(
                                route = "Home"
                            ){ _->
                                HomeScreen(
                                    homeDestinations = homeDestinations,
                                   onHomeExit = {destination->
                                       rootNavController.navigate(destination)
                                   }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}


