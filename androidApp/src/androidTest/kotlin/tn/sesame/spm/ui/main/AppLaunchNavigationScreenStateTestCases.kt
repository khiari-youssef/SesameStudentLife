package tn.sesame.spm.ui.main

import MainActivityScreen
import MainNavigation
import android.content.Context
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.get
import org.koin.test.inject
import org.koin.test.mock.MockProvider
import org.koin.test.mock.declareMock
import tn.sesame.designsystem.SesameTheme
import tn.sesame.designsystem.components.bars.SesameBottomNavigationBarDefaults
import tn.sesame.spm.android.ui.login.LoginState
import tn.sesame.spm.android.ui.main.MainActivity
import tn.sesame.spm.android.ui.main.MainActivityStateHolder
import tn.sesame.spm.di.androidSecurityModule
import tn.sesame.spm.security.SupportedDeviceAuthenticationMethods
import tn.sesame.spm.KoinAndroidUITestRule
import tn.sesame.spm.android.R
import tn.sesame.spm.domain.entities.SesameRole
import tn.sesame.spm.domain.entities.SesameUser
import tn.sesame.spm.domain.entities.SesameUserSex
import tn.sesame.spm.security.BiometricLauncherService
import tn.sesame.spm.ui.testModule


class AppLaunchNavigationScreenStateTestCases : KoinTest{

    private val instrumentationContext: Context by inject()

    @get:Rule
    val composeMainActivityTestRule = createAndroidComposeRule(MainActivity::class.java)


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


    @Test
    fun testMainActivityScreenWhenAnUndefinedBiometricAuthErrorOccurs() {
        composeMainActivityTestRule.activity.setContent {
           SesameTheme {
               composeMainActivityTestRule.activity.run {
                   MainActivityScreen(
                       uiState = MainActivityStateHolder
                           .rememberMainActivityState(
                               biometricSupportState = remember {
                                   mutableStateOf(SupportedDeviceAuthenticationMethods.Undefined)
                               },
                               autoLoginState = remember {
                                   mutableStateOf(LoginState.Loading)
                               },
                               rootNavController =rememberNavController() ,
                               homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
                           ),
                       onCheckBiometricCapabilitiesStateRequest = {

                       })
               }
           }
        }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).run {
            assertExists()
            val titleText = instrumentationContext
                .resources.getString(R.string.error_biometric_temporararely_unavailable_title)
            val subTitleText = instrumentationContext
                .resources.getString(R.string.error_biometric_temporararely_unavailable_message)
            hasAnyChild(
                hasText(titleText) and hasText(subTitleText)
            )
        }

    }

    @Test
    fun testMainActivityScreenWhenANoHardwareBiometricAuthErrorOccurs() {
        composeMainActivityTestRule.activity.setContent {
           SesameTheme {
               composeMainActivityTestRule.activity.run {
                   MainActivityScreen(
                       uiState = MainActivityStateHolder
                           .rememberMainActivityState(
                               biometricSupportState = remember {
                                   mutableStateOf(SupportedDeviceAuthenticationMethods.NoHardware)
                               },
                               autoLoginState = remember {
                                   mutableStateOf(LoginState.Loading)
                               },
                               rootNavController =rememberNavController() ,
                               homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
                           ),
                       onCheckBiometricCapabilitiesStateRequest = {

                       })
               }
           }
        }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).run {
            assertExists()
            val titleText = instrumentationContext
                .resources.getString(R.string.error_unsupported_biometric_features_title)
            val subTitleText = instrumentationContext
                .resources.getString(R.string.error_unsupported_biometric_features_message)
            hasAnyChild(
                hasText(titleText) and hasText(subTitleText)
            )
        }

    }


    @Test
    fun testMainActivityScreenWhenHardwareUnavailableBiometricAuthErrorOccurs() {
        composeMainActivityTestRule.activity.setContent {
           SesameTheme {
               composeMainActivityTestRule.activity.run {
                   MainActivityScreen(
                       uiState = MainActivityStateHolder
                           .rememberMainActivityState(
                               biometricSupportState = remember {
                                   mutableStateOf(SupportedDeviceAuthenticationMethods.NoHardware)
                               },
                               autoLoginState = remember {
                                   mutableStateOf(LoginState.Loading)
                               },
                               rootNavController =rememberNavController() ,
                               homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
                           ),
                       onCheckBiometricCapabilitiesStateRequest = {

                       })
               }
           }
        }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).run {
            assertExists()
            val titleText = instrumentationContext
                .resources.getString(R.string.error_biometric_undefined_title)
            val subTitleText = instrumentationContext
                .resources.getString(R.string.error_biometric_undefined_message)
            hasAnyChild(
                hasText(titleText) and hasText(subTitleText)
            )
        }

    }

    @Test
    fun testMainActivityScreenWhenUnavailableBiometricAuthErrorOccurs() {
        composeMainActivityTestRule.activity.setContent {
           SesameTheme {
               composeMainActivityTestRule.activity.run {
                   MainActivityScreen(
                       uiState = MainActivityStateHolder
                           .rememberMainActivityState(
                               biometricSupportState = remember {
                                   mutableStateOf(SupportedDeviceAuthenticationMethods.NoHardware)
                               },
                               autoLoginState = remember {
                                   mutableStateOf(LoginState.Loading)
                               },
                               rootNavController =rememberNavController() ,
                               homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
                           ),
                       onCheckBiometricCapabilitiesStateRequest = {

                       })
               }
           }
        }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).run {
            assertExists()
            val titleText = instrumentationContext
                .resources.getString(R.string.error_unregistered_biometric_identity_title)
            val subTitleText = instrumentationContext
                .resources.getString(R.string.error_unregistered_biometric_identity_message)
            hasAnyChild(
                hasText(titleText) and hasText(subTitleText) and hasContentDescription("InfoPopupTextButton")
            )
        }

    }


    @Test
    fun testMainActivityScreenWhenDeviceHasARegisteredBiometricIdentityAndUserIsLoggedOut() {
        val mockBiometricLauncherService =get<BiometricLauncherService>()

        composeMainActivityTestRule.activity.setContent {
           SesameTheme {
               composeMainActivityTestRule.activity.run {
                   MainActivityScreen(
                       uiState = MainActivityStateHolder
                           .rememberMainActivityState(
                               biometricSupportState = remember {
                                   mutableStateOf(SupportedDeviceAuthenticationMethods.Available(mockBiometricLauncherService))
                               },
                               autoLoginState = remember {
                                   mutableStateOf(LoginState.Idle)
                               },
                               rootNavController =rememberNavController() ,
                               homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
                           ),
                       onCheckBiometricCapabilitiesStateRequest = {

                       })
               }
           }
        }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).assertDoesNotExist()
        composeMainActivityTestRule.onNodeWithContentDescription(
            "LoginScreen"
        ).assertExists()

    }
    @Test
    fun testMainActivityScreenWhenDeviceHasARegisteredBiometricIdentityAndUserIsAutoLogging() {
        val mockBiometricLauncherService =get<BiometricLauncherService>()

        composeMainActivityTestRule.activity.setContent {
           SesameTheme {
               composeMainActivityTestRule.activity.run {
                   MainActivityScreen(
                       uiState = MainActivityStateHolder
                           .rememberMainActivityState(
                               biometricSupportState = remember {
                                   mutableStateOf(SupportedDeviceAuthenticationMethods.Available(mockBiometricLauncherService))
                               },
                               autoLoginState = remember {
                                   mutableStateOf(LoginState.Loading)
                               },
                               rootNavController =rememberNavController() ,
                               homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
                           ),
                       onCheckBiometricCapabilitiesStateRequest = {

                       })
               }
           }
        }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).assertDoesNotExist()
        composeMainActivityTestRule.onNodeWithContentDescription(
            "LoginScreen"
        ).assertDoesNotExist()
        composeMainActivityTestRule.onNodeWithContentDescription(
            "AutoLoginLoadingScreen"
        ).assertExists()

    }
    @Test
    fun testMainActivityScreenWhenDeviceHasARegisteredBiometricIdentityAndUserIsAutoLoggedIN() {
        val mockBiometricLauncherService =get<BiometricLauncherService>()
        val user = SesameUser(
            "",
            "",
            "",
            "",
            SesameUserSex.Male,
            "",
            SesameRole("")
        )

        composeMainActivityTestRule.activity.setContent {
           SesameTheme {
               composeMainActivityTestRule.activity.run {
                   MainActivityScreen(
                       uiState = MainActivityStateHolder
                           .rememberMainActivityState(
                               biometricSupportState = remember {
                                   mutableStateOf(SupportedDeviceAuthenticationMethods.Available(mockBiometricLauncherService))
                               },
                               autoLoginState = remember {
                                   mutableStateOf(LoginState.Success(user))
                               },
                               rootNavController =rememberNavController() ,
                               homeDestinations = SesameBottomNavigationBarDefaults.getDefaultConfiguration()
                           ),
                       onCheckBiometricCapabilitiesStateRequest = {

                       })
               }
           }
        }
        composeMainActivityTestRule.onNodeWithContentDescription(
            "InfoPopup"
        ).assertDoesNotExist()
        composeMainActivityTestRule.onNodeWithContentDescription(
            "LoginScreen"
        ).assertDoesNotExist()
        composeMainActivityTestRule.onNodeWithContentDescription(
            "AutoLoginLoadingScreen"
        ).assertDoesNotExist()
        composeMainActivityTestRule.onNodeWithContentDescription(
            "HomeScreen"
        ).assertExists()

    }

}