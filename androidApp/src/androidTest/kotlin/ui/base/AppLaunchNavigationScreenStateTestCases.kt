package ui.base

import MainNavigation
import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import tn.sesame.designsystem.SesameTheme
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.ui.main.MainActivity


class AppLaunchNavigationScreenStateTestCases {

    private lateinit var instrumentationContext: Context

    @get:Rule
    val composeMainActivityTestRule = createAndroidComposeRule(MainActivity::class.java)


    @Before
    fun init() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testMainNavigationWhenLoginScreenShouldBeTheStartDestination() {
        composeMainActivityTestRule.activity.setContent {
           SesameTheme {
               composeMainActivityTestRule.activity.run {
                   MainNavigation(
                       modifier = Modifier.fillMaxSize(),
                       rootNavController = rememberNavController(),
                       homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
                   )
               }
           }
        }
        composeMainActivityTestRule
            .onNodeWithContentDescription("LoginButton")
            .assertExists()
    }


    @Test
    fun testMainNavigationWhenLoginScreenIsSkippedForAutologin() {
        composeMainActivityTestRule.activity.setContent {
           SesameTheme {
               composeMainActivityTestRule.activity.run {
                   MainNavigation(
                       modifier = Modifier.fillMaxSize(),
                       rootNavController = rememberNavController(),
                       homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration(),
                       skipLogin = true
                   )
               }
           }
        }
        composeMainActivityTestRule
            .onNodeWithContentDescription("LoginButton")
            .assertDoesNotExist()
        composeMainActivityTestRule
            .onNodeWithContentDescription("HomeScreen")
            .assertExists()

    }

}